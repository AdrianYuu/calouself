package model;

import enums.ItemStatus;
import enums.OfferStatus;
import lib.db.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class Offer {
    private String offerId;
    private String itemId;
    private String buyerId;
    private Integer offerPrice;
    private OfferStatus offerStatus;
    private String declineReason;


    public Offer(String offerId, String itemId, String buyerId, Integer offerPrice, OfferStatus offerStatus, String declineReason) {
        this.offerId = offerId;
        this.itemId = itemId;
        this.buyerId = buyerId;
        this.offerPrice = offerPrice;
        this.offerStatus = offerStatus;
        this.declineReason = declineReason;
    }

    public static boolean create(String itemId, String buyerId, Integer offerPrice, OfferStatus offerStatus) {
        String query = "INSERT INTO offers (item_id, buyer_id, offer_price, offer_status) VALUES (?, ?, ?, ?)";
        return Connect.getConnection().executePreparedUpdate(query, itemId, buyerId, offerPrice, offerStatus.name());
    }

    public static List<Offer> getBySellerId(String sellerId) {
        String query = "SELECT * FROM offers WHERE seller_id = ?";

        List<Offer> offers = new ArrayList<>();


        ResultSet rs = Connect.getConnection().executePreparedQuery(query, sellerId);

        try {
            while (rs.next()) {
                offers.add(
                        new Offer(
                                rs.getString("offer_id"),
                                rs.getString("item_id"),
                                rs.getString("buyer_id"),
                                rs.getInt("offer_price"),
                                OfferStatus.valueOf(rs.getString("offer_status")),
                                rs.getString("decline_reason")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offers;
    }

    public static List<Offer> getByItemId(String itemId) {
        String query = "SELECT * FROM offers WHERE item_id = ?";

        List<Offer> offers = new ArrayList<>();


        ResultSet rs = Connect.getConnection().executePreparedQuery(query, itemId);

        try {
            while (rs.next()) {
                offers.add(
                        new Offer(
                                rs.getString("offer_id"),
                                rs.getString("item_id"),
                                rs.getString("buyer_id"),
                                rs.getInt("offer_price"),
                                OfferStatus.valueOf(rs.getString("offer_status")),
                                rs.getString("decline_reason")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offers;
    }
    public static Offer getById(String offerId) {
        String query = "SELECT * FROM offers WHERE offer_id = ? LIMIT 1";

        try {
            ResultSet rs = Connect.getConnection().executePreparedQuery(query, offerId);

            if (rs.next()) {
                new Offer(
                        rs.getString("offer_id"),
                        rs.getString("item_id"),
                        rs.getString("buyer_id"),
                        rs.getInt("offer_price"),
                        OfferStatus.valueOf(rs.getString("offer_status")),
                        rs.getString("decline_reason")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean update(String offerId, String itemId, String buyerId, Integer offerPrice, OfferStatus offerStatus, String declineReason) {
        String query = "UPDATE offers" +
                "SET item_id = ?," +
                "buyer_id = ?," +
                "offer_price = ?," +
                "offer_status = ?" +
                "decline_reason = ?" +
                "WHERE offer_id = ?";

        return Connect.getConnection().executePreparedUpdate(query, itemId, buyerId, offerPrice, offerStatus.name(), offerId);
    }

    public String getOfferId() {
        return offerId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public Integer getOfferPrice() {
        return offerPrice;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public String getDeclineReason() {
        return declineReason;
    }

    public void setDeclineReason(String declineReason) {
        this.declineReason = declineReason;
    }
}