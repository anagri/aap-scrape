package com.creativei.aap;

import java.sql.Date;

public class Donation {
    private final int id;
    public final String name;
    public final String country;
    public final String state;
    public final String district;
    public final String transactionId;
    public final String amountStr;
    public final Integer amount;
    public final String dateStr;
    public final Date date;

    public Donation(int id, String name, String country, String state, String district, String transactionId, String amountStr,
                    Integer amount, String dateStr, Date date) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.state = state;
        this.district = district;
        this.transactionId = transactionId;
        this.amountStr = amountStr;
        this.amount = amount;
        this.dateStr = dateStr;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Donation donation = (Donation) o;

        if (id != donation.id) return false;
        if (amount != null ? !amount.equals(donation.amount) : donation.amount != null) return false;
        if (amountStr != null ? !amountStr.equals(donation.amountStr) : donation.amountStr != null) return false;
        if (country != null ? !country.equals(donation.country) : donation.country != null) return false;
        if (date != null ? !date.equals(donation.date) : donation.date != null) return false;
        if (dateStr != null ? !dateStr.equals(donation.dateStr) : donation.dateStr != null) return false;
        if (district != null ? !district.equals(donation.district) : donation.district != null) return false;
        if (name != null ? !name.equals(donation.name) : donation.name != null) return false;
        if (state != null ? !state.equals(donation.state) : donation.state != null) return false;
        if (transactionId != null ? !transactionId.equals(donation.transactionId) : donation.transactionId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (transactionId != null ? transactionId.hashCode() : 0);
        result = 31 * result + (amountStr != null ? amountStr.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (dateStr != null ? dateStr.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", district='" + district + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", amountStr='" + amountStr + '\'' +
                ", amount=" + amount +
                ", dateStr='" + dateStr + '\'' +
                ", date=" + date +
                '}';
    }
}
