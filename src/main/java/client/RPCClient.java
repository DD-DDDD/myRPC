package client;

import bean.RPCRequest;
import bean.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
