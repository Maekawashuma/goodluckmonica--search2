package jp.co.fim.market.logic;

import jp.co.flm.market.dao.CategoryDAO;
import jp.co.flm.market.dto.Category;
import jp.co.fim.market.utils.ConnectionManager;
import jp.co.fim.market.exceptions.MarketSystemException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CommonshowTopPageLogic {
    // トップページに表示するカテゴリ情報を取得するメソッド
    // 戻り値: カテゴリ情報を格納したList<Category>オブジェクト
    // 例外: MarketSystemException データベースアクセスエラーが発生した場合
    public List<Category> getTopPageCategories() throws MarketSystemException {
        Connection con = null;
        List<Category> categoryList = null;

        try {
            // データベース接続を取得
            con = ConnectionManager.getConnection();

            // CategoryDAOオブジェクトを作成
            CategoryDAO categoryDAO = new CategoryDAO(con);

            // CATEGORYテーブルから全カテゴリ情報を取得し、降順にソートして返す
            categoryList = categoryDAO.returnTop();

        } catch (SQLException e) {
            // SQL例外が発生した場合にMarketSystemExceptionをスロー
            throw new MarketSystemException("システムエラーです。システム管理者に連絡してください。", e);
        } finally {
            // データベース接続をクローズ
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    // クローズ時に例外が発生した場合にもMarketSystemExceptionをスロー
                    throw new MarketSystemException("システムエラーです。システム管理者に連絡してください。", e);
                }
            }
        }

        return categoryList;
    }
}