package usa.bogdan.ENS.Entity;

import java.util.List;

public class KafkaResend {
    private List<ENSentity> listOK;

    public KafkaResend() {

    }

    public KafkaResend(List<ENSentity> listOK) {
        this.listOK = listOK;
    }

    public List<ENSentity> getListOK() {
        return listOK;
    }

    public void setListOK(List<ENSentity> listOK) {
        this.listOK = listOK;
    }

    @Override
    public String toString() {
        return "KafkaResend{" +
                "listOK=" + listOK +
                '}';
    }
}
