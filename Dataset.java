
// this class save one dataset from JXMapMatcherVer3

public class Dataset {
	public long objID;
	public static long objCount = 0;
	
	public String type = "";
	public String down_up = "";
	public long timestamp = -1;
	public double matched_latitude = -1;
	public double matched_longitude = -1;
	public double unmatched_latitude = -1;
	public double unmatched_longitude = -1;
	public double unMatched_distance = -1;
	public long startNode_id = -1;
	public long endNode_id = -1;
	public String edge_id_str = "";
	public double length_in_edge = -1;
	public double length_of_edge = -1;
	public double matched_distribution_in_WayPart = -1;
	public int datarate = -1;
	public double delay = -1;
	public double loss_rate = -1;
	public int matchedLinkNr = -1;
	public String WCDMA_Ch = "";
	public String WCDMA_SC = "";
	public String GSM_CellId = "";
	public String GSM_LAC = "";
	public int matchedLinkNrGlobal = -1;
	
	public int CellId = -1;

	public Dataset() {
		this.objID = Dataset.objCount;
		Dataset.objCount++;
	}
}
