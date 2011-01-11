package kwitches.text;

/**
 * １行文字列変換インターフェース
 * @author voidy21
 */
public interface LineMessageTransformInterface {
    
    /**
     * #transform(java.lang.String)を実行するための正規表現を返す
     * @return #transform(java.lang.String)を実行するための正規表現
     */
    String getRegexp();

    /**
     * 文字列を指定されたフォーマットで変換する
     * @args 変換したい文字列
     * @return 変換された文字列
     */
    String transform(String rawString);
}
