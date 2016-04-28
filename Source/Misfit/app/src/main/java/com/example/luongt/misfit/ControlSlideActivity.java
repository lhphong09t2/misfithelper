package com.example.luongt.misfit;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.luongt.misfit.adapter.ServerAdapter;
import com.example.luongt.misfit.misfithelper.ControlSlideHelper;
import com.example.luongt.misfit.service.HelloService;
import com.onballgroup.cominlan.client.ComInLanClient;
import com.onballgroup.cominlan.client.OnBroadcastClientListener;
import com.onballgroup.cominlan.model.CServer;
import com.onballgroup.cominlan.model.IServer;
import com.onballgroup.cominlan.model.OnServerListener;
import com.onballgroup.cominlan.model.ServerState;

import java.util.List;

public class ControlSlideActivity extends AppCompatActivity implements OnBroadcastClientListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView _serverListView;
    private ServerAdapter _arrayAdapter;

    private ControlSlideHelper _controlSlideHelper;

    private Dialog _dialog;
    private Button _cancelButton;
    private Button _okButton;
    private EditText _passcodeSlideET;

    private LinearLayout _controlArea;
    private Button _backSlideButton;
    private Button _refreshSlideButton;
    private Button _nextSlideButton;

    private ComInLanClient _comInLanClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_slide);

        _controlSlideHelper = HelloService.getInstance().getControlSlideHelper();

        _comInLanClient = _controlSlideHelper.getComInLanClient();
        _comInLanClient.setOnComInClientListener(this);

        initView();
    }

    @Override
    protected void onDestroy() {
        _comInLanClient.setOnComInClientListener(null);
        if (_controlSlideHelper.getServer() != null) {
            _controlSlideHelper.getServer().setOnServerListener(null);
        }
        super.onDestroy();
    }

    private void initView() {

        _dialog = new Dialog(this);
        _dialog.setContentView(R.layout.dialog_passcode_slide);
        _cancelButton = (Button) _dialog.findViewById(R.id.cancelButton);
        _cancelButton.setOnClickListener(this);
        _okButton = (Button) _dialog.findViewById(R.id.okButton);
        _okButton.setOnClickListener(this);
        _passcodeSlideET = (EditText) _dialog.findViewById(R.id.passcodeSlideET);

        _controlArea = (LinearLayout) findViewById(R.id.controlArea);
        _backSlideButton = (Button) findViewById(R.id.backSlideButton);
        _backSlideButton.setOnClickListener(this);
        _refreshSlideButton = (Button) findViewById(R.id.refreshSlideButton);
        _refreshSlideButton.setOnClickListener(this);
        _nextSlideButton = (Button) findViewById(R.id.nextSlideButton);
        _nextSlideButton.setOnClickListener(this);

        _serverListView = (ListView) findViewById(R.id.serverListView);
        _arrayAdapter = new ServerAdapter(this, 0, _comInLanClient.getServers());
        _serverListView.setAdapter(_arrayAdapter);
        _serverListView.setOnItemClickListener(this);

        if (_controlSlideHelper.getServer() != null) {
            _controlArea.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onServerNewFound(IServer server) {
        server.setOnServerListener(new OnServerListener() {
            @Override
            public void onStateChanged(final IServer server) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _arrayAdapter.notifyDataSetChanged();

                        int checkedItemPosition = _serverListView.getCheckedItemPosition();

                        if (checkedItemPosition < 0) {
                            return;
                        }

                        if (server.getState() == ServerState.PasscodeRequested) {
                                _dialog.show();
                            //sendPasscode();
                        }

                        if (server.getState() == ServerState.Connected) {
                            _controlSlideHelper.setServer(server);
                            _controlArea.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }

            @Override
            public void onDataReceived(IServer server, String data) {

            }
        });
    }

    @Override
    public void onServerChanged(final IServer iServer) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _arrayAdapter.notifyDataSetChanged();
                if (iServer.getState() == ServerState.None) {
                    _controlArea.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onServerRemoved(final IServer iServer) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _controlSlideHelper.setServer(null);
                _controlArea.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onServersChanged(List<IServer> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int checkedItemPosition = _serverListView.getCheckedItemPosition();

        if (checkedItemPosition == position) {
            IServer checkedServer = _arrayAdapter.getItem(checkedItemPosition);
            if (checkedServer.getState() == ServerState.Connected) {
                return;
            }
        } else {
            IServer checkedServer = _arrayAdapter.getItem(checkedItemPosition);
            checkedServer.setOnServerListener(null);
            _comInLanClient.disconnect(checkedServer);
            _controlArea.setVisibility(View.INVISIBLE);
            _controlSlideHelper.setServer(null);
        }

        _serverListView.setItemChecked(position, true);
        IServer server = _arrayAdapter.getItem(position);

        if (server.getState() == ServerState.None) {
            _comInLanClient.connect(server);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == _cancelButton) {
            _dialog.dismiss();
            disconnect();
        }

        if (v == _okButton) {
            sendPasscode();
            _dialog.dismiss();
        }

        if (v == _backSlideButton) {
            _controlSlideHelper.sendData("b");
        }
        if (v == _refreshSlideButton) {
            _controlSlideHelper.sendData("f");
        }
        if (v == _nextSlideButton) {
            _controlSlideHelper.sendData("n");
        }
    }

    private void sendPasscode() {
        int checkedItemPosition = _serverListView.getCheckedItemPosition();
        if (checkedItemPosition < 0) {
            return;
        }
        try {
            CServer checkedServer = (CServer) _serverListView.getItemAtPosition(checkedItemPosition);
            if (checkedServer != null) {
                _comInLanClient.sendPasscode(checkedServer, _passcodeSlideET.getText().toString());
//                _comInLanClient.sendPasscode(checkedServer, "");
            }
        } catch (IndexOutOfBoundsException e) {
            return;
        }
    }

    private void disconnect() {
        int checkedItemPosition = _serverListView.getCheckedItemPosition();
        if (checkedItemPosition < 0) {
            return;
        }
        try {
            CServer checkedServer = (CServer) _serverListView.getItemAtPosition(checkedItemPosition);
            if (checkedServer != null) {
                _comInLanClient.disconnect(checkedServer);
            }
        } catch (IndexOutOfBoundsException e) {
            return;
        }
    }
}