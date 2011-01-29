package kwitches.message;

import kwitches.model.BBSDataModel;

public interface SignMessageInterface extends MessageInterface {
    BBSDataModel getInformation();
    void setInformation(BBSDataModel bbsDataModel);
}
