package kwitches.text.hyperlink;

import kwitches.text.LineMessageTransformInterface;

public interface HyperlinkTransformInterface extends LineMessageTransformInterface {
    /**
     * リンクの種類による記事のタイプを返す
     * EX) 投稿文字列が"http://twitter.com/voidy21/status/23919746034438144"の場合、
     *      タイプは"twitter"
     * @return 記事のタイプ
     */
    String getArticleType();
}
