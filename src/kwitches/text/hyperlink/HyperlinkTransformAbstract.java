/**
 * 
 */
package kwitches.text.hyperlink;

import java.util.HashMap;

/**
 * @author voidy21
 *
 */
public abstract class HyperlinkTransformAbstract
    implements HyperlinkTransformInterface{

    private HashMap<String, String> accountTable;

    /**
     * @param accountTable セットする accountTable
     */
    public void setAccountTable(HashMap<String, String> accountTable) {
        this.accountTable = accountTable;
    }

    /**
     * @return accountTable
     */
    public HashMap<String, String> getAccountTable() {
        return accountTable;
    }

}
