import java.util.Vector;


public class CellIdentification {

	public int CellID = -1;
	
	public String WCDMA_Ch;
	public String WCDMA_SC;
	public String GSM_CellId;
	public String GSM_LAC;

	public double min_latitude = Double.MAX_VALUE;
	public double min_longitude = Double.MAX_VALUE;
	public double max_latitude = Double.MIN_VALUE;
	public double max_longitude = Double.MIN_VALUE;

	public static double DistanceToNextCell = 5000;
	
	public Vector<Dataset> Datasets = new Vector<Dataset>();
	
	public CellIdentification(int id, Dataset od) {
		this.CellID = id;
		this.WCDMA_Ch = od.WCDMA_Ch;
		this.WCDMA_SC = od.WCDMA_SC;
		this.GSM_CellId = od.GSM_CellId;
		this.GSM_LAC = od.GSM_LAC;
		this.addToGroup(od);
	}
	
	/**
	 *  check if ds is in this mobile cell
	 * @param ds
	 * @return true if ds is in this mobile cell
	 */
	public boolean isInGroup(Dataset ds) {

		if (this.WCDMA_Ch.equals(ds.WCDMA_Ch) && this.WCDMA_SC.equals(ds.WCDMA_SC) && 
				this.GSM_CellId.equals(ds.GSM_CellId) && this.GSM_LAC.equals(ds.GSM_LAC)) {

			double lat = max_latitude - min_latitude;
			lat = lat / 2;
			lat += min_latitude;
			
			double lon = max_longitude - min_longitude;
			lon /= 2;
			lon += min_longitude;
			
			double d = CellIdentification.getDistance(lat, lon, ds.matched_latitude, ds.matched_longitude);
			
			if ( d <= DistanceToNextCell ) {
				return true;
			} else {
				d++;
			}
		}
		
		return false;
	}
	
	/**
	 * add dataset ds to this mobile cell
	 * 
	 * @param ds dataset to add
	 */
	public void addToGroup(Dataset ds) {
		if (ds.matched_latitude < min_latitude) {
			min_latitude = ds.matched_latitude;
		} 
		if (max_latitude < ds.matched_latitude) {
			max_latitude = ds.matched_latitude;
		}
		
		if (ds.matched_longitude < min_longitude) {
			min_longitude = ds.matched_longitude;
		} 
		if (max_longitude < ds.matched_longitude) {
			max_longitude = ds.matched_longitude;
		}
		
		ds.CellId = CellID;
		Datasets.add(ds);
	}
	
	/**
	 *  copy all datasets from cid2 to this mobiel cell
	 * 
	 * @param cid2
	 */
	public void copyDatasetsFrom(CellIdentification cid2) {
		for (int i=0; i<cid2.Datasets.size(); i++) {
			Dataset ds = cid2.Datasets.elementAt(i);
			
			this.addToGroup(ds);
		}
	}
	
	/**
	 * calculate the distance of two mobile cells in meters
	 * 
	 * @param cid1
	 * @param cid2
	 * @return distance in meters
	 */
	public static double getDistance(CellIdentification cid1, CellIdentification cid2) {

		double lat1 = cid1.max_latitude - cid1.min_latitude;
		lat1 = lat1 / 2;
		lat1 += cid1.min_latitude;
		
		double lon1 = cid1.max_longitude - cid1.min_longitude;
		lon1 /= 2;
		lon1 += cid1.min_longitude;

		double lat2 = cid2.max_latitude - cid2.min_latitude;
		lat2 = lat2 / 2;
		lat2 += cid2.min_latitude;
		
		double lon2 = cid2.max_longitude - cid2.min_longitude;
		lon2 /= 2;
		lon2 += cid2.min_longitude;
		
	    return getDistance(lat1, lon1, lat2, lon2);
	}
	
	/**
	 * calculate the distance of two GPS coordinates in meters
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return distance in meters
	 */
	public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
	    double earthRadius = 6371000; //meters
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLon = Math.toRadians(lon2-lon1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLon/2) * Math.sin(dLon/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = (earthRadius * c);

	    return dist;
	}
}
