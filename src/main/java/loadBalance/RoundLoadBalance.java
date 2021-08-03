package loadBalance;

import bean.RPCRequest;

import java.util.List;

public class RoundLoadBalance extends AbstractLoadBalance{
    private int choose = -1;

    @Override
    protected String doSelect(List<String> serviceAddresses, RPCRequest rpcRequest) {
        choose++;
        choose = choose%serviceAddresses.size();
        return serviceAddresses.get(choose);
    }
}
