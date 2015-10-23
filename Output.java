import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

// this class load and save the datasets from JXMapMatcherVer3

public class Output {
	
	public Vector<Dataset> Datasets = new Vector<Dataset>();
	
	/**
	 * load datasets from JXMapMatcherVer3
	 * 
	 * @param FilePath path of datasets from JXMapMatcherVer3
	 */
	public void loadFile(String FilePath) {
		
		Datasets.clear();
	
		String line = "";
		String [] Columns = null;;
		
		try{
			
			System.out.println("loading ... " + FilePath);
			
			BufferedReader bReader = new BufferedReader( new InputStreamReader( new FileInputStream( new File( FilePath ) ), "UTF-8" ));
			
			line = bReader.readLine();
			
			int typeColumnNr = -1;
			int down_upColumnNr = -1;
			int timestampColumnNr = -1;
			int matched_latitudeColumnNr = -1;
			int matched_longitudeColumnNr = -1;
			int unmatched_latitudeColumnNr = -1;
			int unmatched_longitudeColumnNr = -1;
			int startNode_idColumnNr = -1;
			int endNode_idColumnNr = -1;
			int edge_id_strColumnNr = -1;
			int length_in_edgeColumnNr = -1;
			int length_of_edgeColumnNr = -1;
			int matched_distribution_in_WayPartColumnNr = -1;
			int datarateColumnNr = -1;
			int delayColumnNr = -1;
			int loss_rateColumnNr = -1;
			int matchedLinkNrColumnNr = -1;
			int WCDMA_ChColumnNr = -1;
			int WCDMA_SCColumnNr = -1;
			int GSM_CellIdColumnNr = -1;
			int GSM_LACColumnNr = -1;
			int unMatched_distanceColumnNr = -1;
			
			Columns = line.split(",");
			
			for (int i=0; i < Columns.length; i++) {
				if (Columns[i].equals("type")) {
					typeColumnNr = i;
				} else if (Columns[i].equals("down_up")) {
					down_upColumnNr = i;
				} else if (Columns[i].equals("timestamp")) {
					timestampColumnNr = i;
				} else if (Columns[i].equals("matched_latitude")) {
					matched_latitudeColumnNr = i;
				} else if (Columns[i].equals("matched_longitude")) {
					matched_longitudeColumnNr = i;
				} else if (Columns[i].equals("unmatched_latitude")) {
					unmatched_latitudeColumnNr = i;
				} else if (Columns[i].equals("unmatched_longitude")) {
					unmatched_longitudeColumnNr = i;
				} else if (Columns[i].equals("startNode_id")) {
					startNode_idColumnNr = i;
				} else if (Columns[i].equals("endNode_id")) {
					endNode_idColumnNr = i;
				} else if (Columns[i].equals("edge_id_str")) {
					edge_id_strColumnNr = i;
				} else if (Columns[i].equals("length_in_edge")) {
					length_in_edgeColumnNr = i;
				} else if (Columns[i].equals("length_of_edge")) {
					length_of_edgeColumnNr = i;
				} else if (Columns[i].equals("matched_distribution_in_WayPart")) {
					matched_distribution_in_WayPartColumnNr = i;
				} else if (Columns[i].equals("datarate")) {
					datarateColumnNr = i;
				} else if (Columns[i].equals("delay")) {
					delayColumnNr = i;
				} else if (Columns[i].equals("loss_rate")) {
					loss_rateColumnNr = i;
				} else if (Columns[i].equals("matchedLinkNr")) {
					matchedLinkNrColumnNr = i;
				} else if (Columns[i].equals("WCDMA_Ch")) {
					WCDMA_ChColumnNr = i;
				} else if (Columns[i].equals("WCDMA_SC")) {
					WCDMA_SCColumnNr = i;
				} else if (Columns[i].equals("GSM_CellId")) {
					GSM_CellIdColumnNr = i;
				} else if (Columns[i].equals("GSM_LAC")) {
					GSM_LACColumnNr = i;
				} else if (Columns[i].equals("unMatched_distance")) {
					unMatched_distanceColumnNr = i;
				}
				
			}
			
			line = bReader.readLine();
			while (line != null) {
				Dataset od = new Dataset();
				
				Columns = line.split(",");
				
				od.type = Columns[typeColumnNr];
				od.down_up = Columns[down_upColumnNr];
				od.timestamp = Long.parseLong(Columns[timestampColumnNr]);
				od.matched_latitude = Double.parseDouble(Columns[matched_latitudeColumnNr]);
				od.matched_longitude = Double.parseDouble(Columns[matched_longitudeColumnNr]);
				od.unmatched_latitude = Double.parseDouble(Columns[unmatched_latitudeColumnNr]);
				od.unmatched_longitude = Double.parseDouble(Columns[unmatched_longitudeColumnNr]);
				od.startNode_id = Long.parseLong(Columns[startNode_idColumnNr]);
				od.endNode_id = Long.parseLong(Columns[endNode_idColumnNr]);
				od.edge_id_str = Columns[edge_id_strColumnNr];
				od.length_in_edge = Double.parseDouble(Columns[length_in_edgeColumnNr]);
				od.length_of_edge = Double.parseDouble(Columns[length_of_edgeColumnNr]);
				od.matched_distribution_in_WayPart = Double.parseDouble(Columns[matched_distribution_in_WayPartColumnNr]);
				od.datarate = Integer.parseInt(Columns[datarateColumnNr]);
				od.delay = Double.parseDouble(Columns[delayColumnNr]);
				od.loss_rate = Double.parseDouble(Columns[loss_rateColumnNr]);
				od.matchedLinkNr = Integer.parseInt(Columns[matchedLinkNrColumnNr]);
				od.WCDMA_Ch = Columns[WCDMA_ChColumnNr];
				od.WCDMA_SC = Columns[WCDMA_SCColumnNr];
				od.GSM_CellId = Columns[GSM_CellIdColumnNr];
				od.GSM_LAC = Columns[GSM_LACColumnNr];
				od.unMatched_distance = Double.parseDouble(Columns[unMatched_distanceColumnNr]);
				
				Datasets.add(od);
				
				line = bReader.readLine();
			}
			
			bReader.close();
		} catch (Exception e) {			
			System.out.println("Error: \n" + e.toString());
			System.exit(-1);
		}
		
	}
}
