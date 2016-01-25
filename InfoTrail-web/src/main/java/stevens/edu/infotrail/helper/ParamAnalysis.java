package stevens.edu.infotrail.helper;

import javax.ws.rs.core.UriInfo;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public class ParamAnalysis {
    String requestId;
    String typeFilter;
    String query;
    double qX;
    double qY;
    String[] bIds;
    int sRH;
    int sRW;
    int parId;
    int inX;
    int inY;
    int limit;
    int[] offset;

    /**
     * Constructor
     */
    public ParamAnalysis() {
        requestId = "";
        typeFilter = "All";
        query = "";
        qX = Double.MIN_VALUE;
        qY = Double.MIN_VALUE;
        bIds = null;
        sRH = Integer.MIN_VALUE;
        sRW = Integer.MIN_VALUE;
        parId = Integer.MIN_VALUE;
        inX = Integer.MIN_VALUE;
        inY = Integer.MIN_VALUE;
        limit = Integer.MIN_VALUE;
        offset = null;

    }

    /**
     * @param info UriInfo
     */
    public void doProcess(UriInfo info) {
        requestId = info.getQueryParameters().getFirst("requestID");
        typeFilter = (info.getQueryParameters().getFirst("typeFilter") != null) ? info.getQueryParameters().getFirst("typeFilter") : "All";
        query = info.getQueryParameters().getFirst("query");
        String queryX = info.getQueryParameters().getFirst("queryLongitute");
        qX = (queryX != null) ? toDouble(queryX) : Double.MIN_VALUE;
        String queryY = info.getQueryParameters().getFirst("queryLatitude");
        qY = (queryY != null) ? toDouble(queryY) : Double.MIN_VALUE;
        String beacon_ids = info.getQueryParameters().getFirst("nearbyBeacons");
        bIds = (beacon_ids != null) ? beacon_ids.split(",") : null;
        String searchRegionH = info.getQueryParameters().getFirst("searchRegionHeight");
        sRH = (searchRegionH != null) ? toInteger(searchRegionH) : 500;
        String searchRegionW = info.getQueryParameters().getFirst("searchRegionWidth");
        sRW = (searchRegionW != null) ? toInteger(searchRegionW) : 500;
        String parentId = info.getQueryParameters().getFirst("parentID");
        parId = (parentId != null) ? toInteger(parentId) : Integer.MIN_VALUE;
        String indoorX = info.getQueryParameters().getFirst("indoorLocationX");
        inX = (indoorX != null) ? toInteger(indoorX) : Integer.MIN_VALUE;
        String indoorY = info.getQueryParameters().getFirst("indoorLocationY");
        inY = (indoorY != null) ? toInteger(indoorY) : Integer.MIN_VALUE;
        String tmpLimit = info.getQueryParameters().getFirst("limit");
        limit = (tmpLimit != null) ? toInteger(tmpLimit) : Integer.MIN_VALUE;
        String tmpOffset = info.getQueryParameters().getFirst("offset");
        offset = (tmpOffset != null) ? toIntegerArray(tmpOffset.split(",")) : null;
    }

    public String getTypeFilter() {
        return typeFilter;
    }

    public double getqX() {
        return qX;
    }

    public double getqY() {
        return qY;
    }

    public String[] getbIds() {
        return bIds;
    }

    public int getsRH() {
        return sRH;
    }

    public int getsRW() {
        return sRW;
    }

    public int getParId() {
        return parId;
    }

    public int getInX() {
        return inX;
    }

    public int getInY() {
        return inY;
    }

    public int getLimit() {
        return limit;
    }

    public int[] getOffset() {
        return offset;
    }

    /**
     * @param str String
     * @return Integer
     */
    public int toInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }

    /**
     * @param str String array
     * @return Integer array
     */
    private int[] toIntegerArray(String[] str) {
        try {
            int[] intArray = new int[str.length];
            for (int i = 0; i < intArray.length; i++) {
                intArray[i] = Integer.parseInt(str[i]);
            }
            return intArray;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * @param str String
     * @return Double
     */
    public double toDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return Double.MIN_VALUE;
        }
    }


}
