import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Vector;


public class DataUnion {
	private Vector<Output> Outputs = new Vector<Output>();
	private Vector<CellIdentification> CellIds = new Vector<CellIdentification>();
	private DataAggregation dataAggregation = new DataAggregation();
	private Vector<String> inputFiles = new Vector<String>();
	private String outputFile = "";

	public static void main(String[] args) {	

		DataUnion s = new DataUnion();
		
		s.checkArgs(args);

		// load data from files
		for (int i = 0; i < s.inputFiles.size(); i++) {
			Output o = new Output();
			o.loadFile(s.inputFiles.get(i));
			s.Outputs.add(o);
		}

		s.setSearchCellId();

		s.setMatchedLinkNrGlobal();

		s.aggreate();
		
		s.dataAggregation.writeSumToFile(s.outputFile + ".Sum.csv");
		
		if (DataAggregation.GroupSelection.equals("all")) {
			s.writeOutput(s.outputFile);			
		} else {
			s.dataAggregation.writeOutput(s.outputFile);
		}


		System.out.println(" :-) ");
	}

	/**
	 * search and set CellId
	 */
	public void setSearchCellId() {
		
		// search and set CellId from datasets
		for (int i = 0; i < Outputs.size(); i++) {
			Output op = Outputs.get(i);

			for ( int j = 0; j < op.Datasets.size(); j++) {
				Dataset ds = op.Datasets.get(j);

				boolean isInGroup = false;

				for (int k = 0; k < CellIds.size(); k++) {
					CellIdentification cid = CellIds.get(k);

					if (cid.isInGroup(ds)) {
						isInGroup = true;
						cid.addToGroup(ds);
						break;
					}
				}

				if (isInGroup == false) {
					CellIdentification cid = new CellIdentification(CellIds.size(), ds);
					CellIds.add(cid);
				}
			}
		}
		
		// check if mobile cell can be merged
		boolean restart = false;
		outer:
		for (int i = 0; i < CellIds.size(); i++) {
			
			if (restart) {
				i = 0;
				restart = false;
			}
			
			for (int j = i+1; j < CellIds.size(); j++) {
				
				CellIdentification cid1 = CellIds.elementAt(i);
				CellIdentification cid2 = CellIds.elementAt(j);
				
				if (cid1.WCDMA_Ch.equals(cid2.WCDMA_Ch) && cid1.WCDMA_SC.equals(cid2.WCDMA_SC) && 
						cid1.GSM_CellId.equals(cid2.GSM_CellId) && cid1.GSM_LAC.equals(cid2.GSM_LAC)) {
					
					if (CellIdentification.getDistance(cid1, cid2) <= CellIdentification.DistanceToNextCell) {

						cid1.copyDatasetsFrom(cid2);
						
						CellIds.remove(j);
						
						restart = true;
						i = -1;
						continue outer; 						
					}
				}
			}
		}
	}
	
	
	public void setMatchedLinkNrGlobal() {
		int lastMatchedLinkNr = -1;
		int matchedLinkNrGlobal = 0;
		
		for (int i = 0; i < Outputs.size(); i++) {
			Output op = Outputs.get(i);

			matchedLinkNrGlobal++;
			
			for ( int j = 0; j < op.Datasets.size(); j++) {
				Dataset od = op.Datasets.get(j);
				
				if (lastMatchedLinkNr != od.matchedLinkNr) {
					matchedLinkNrGlobal++;
				}					

				if (od.type.equals("Start") || od.type.equals("Real") || od.type.equals("End")) {
					od.matchedLinkNrGlobal = matchedLinkNrGlobal;
				} else {
					od.matchedLinkNrGlobal = (-1 * matchedLinkNrGlobal);
				}
				
				lastMatchedLinkNr = od.matchedLinkNr;				
			}
		}
	}
	
	/**
	 * save datasets if no aggregation
	 * 
	 * @param outputFile path of file
	 */
	public void writeOutput(String outputFile) {
		try {
			
			System.out.println("write  ... " + outputFile);
			
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(outputFile));
			
			String a = "type,down_up,timestamp,"
					+ "matched_latitude,matched_longitude,unmatched_latitude,unmatched_longitude,unMatched_distance,"
					+ "startNode_id,endNode_id,edge_id_str,length_in_edge,length_of_edge,"
					+ "matched_distribution_in_WayPart,"
					+ "datarate,delay,loss_rate,matchedLinkNr,"
					+ "WCDMA_Ch,WCDMA_SC,GSM_CellId,GSM_LAC,CellId,matchedLinkNrGlobal";
			
			bWriter.write(a);
			bWriter.newLine();
			
			for (int i = 0; i < Outputs.size(); i++) {
				Output op = Outputs.get(i);

				for ( int j = 0; j < op.Datasets.size(); j++) {
					Dataset od = op.Datasets.get(j);
					
					bWriter.write(od.type + ",");
					bWriter.write(od.down_up + ",");
					bWriter.write(od.timestamp + ",");
					bWriter.write(od.matched_latitude + ",");
					bWriter.write(od.matched_longitude + ",");
					bWriter.write(od.unmatched_latitude + ",");
					bWriter.write(od.unmatched_longitude + ",");
					bWriter.write(od.unMatched_distance + ",");
					bWriter.write(od.startNode_id + ",");
					bWriter.write(od.endNode_id + ",");
					bWriter.write(od.edge_id_str + ",");
					bWriter.write(od.length_in_edge + ",");
					bWriter.write(od.length_of_edge + ",");
					bWriter.write(od.matched_distribution_in_WayPart + ",");
					bWriter.write(od.datarate + ",");
					bWriter.write(od.delay + ",");
					bWriter.write(od.loss_rate + ",");
					bWriter.write(od.matchedLinkNr + ",");
					bWriter.write(od.WCDMA_Ch + ",");
					bWriter.write(od.WCDMA_SC + ",");
					bWriter.write(od.GSM_CellId + ",");
					bWriter.write(od.GSM_LAC + ",");
					bWriter.write(od.CellId + ",");
					bWriter.write("" + od.matchedLinkNrGlobal);

					bWriter.newLine();
										
				}
				
			}
			
			bWriter.close();
		} catch (Exception e) {
			System.out.println("Error: writeOutput: \n" + e.toString());
			System.exit(-1);
		}
	}

	public void aggreate() {		
		for (int i = 0; i < Outputs.size(); i++) {
			Output op = Outputs.get(i);			
			for ( int j = 0; j < op.Datasets.size(); j++) {
				Dataset ds = op.Datasets.get(j);
				dataAggregation.addDataset(ds);
			}
		}
		
		dataAggregation.aggregation();
	}
	
	/**
	 * check the arg of user
	 * 
	 * @param args
	 */
	public void checkArgs(String[] args) {
		try {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-i")) {
					i++;
					inputFiles.add(args[i]);
				} else if (args[i].equals("-o")) {
					i++;
					outputFile = args[i];
				} else if (args[i].equals("-d")) {
					i++;
					CellIdentification.DistanceToNextCell = Double.parseDouble(args[i]);
				} else if (args[i].equals("-r")) {
					i++;
					DataAggregation.RepresentativeCalculationType = args[i];
				} else if (args[i].equals("-g")) {
					i++;
					DataAggregation.GroupSelection = args[i];
				} else if (args[i].equals("-t")) {
					i++;
					DataAggregation.RepresentativeDataType = args[i];
				} else if (args[i].equals("-pr")) {
					i++;
					if (args[i].equals("no")) {
						DA_EdgeGroups.preferReal = false;
					} else if (args[i].equals("yes")) {
						DA_EdgeGroups.preferReal = true;
					} else {
						System.out.println("Error: unknown parameter: " + args[i]);
						printParameterInfo();
						System.exit(-1);
					}
				} else {
					System.out.println("Error: unknown parameter: " + args[i]);
					printParameterInfo();
					System.exit(-1);
				}
			}
		} catch (Exception e) {
			printParameterInfo();
			System.exit(-1);
		}

		if (inputFiles.size() == 0 || outputFile.equals("")) {
			printParameterInfo();
			System.exit(-1);
		}

		if (false == (DataAggregation.RepresentativeCalculationType.equals("min") || DataAggregation.RepresentativeCalculationType.equals("max") || 
				DataAggregation.RepresentativeCalculationType.equals("avg") || DataAggregation.RepresentativeCalculationType.equals("med") || 
				DataAggregation.RepresentativeCalculationType.equals("minstde") || DataAggregation.RepresentativeCalculationType.equals("maxstde") || 
				DataAggregation.RepresentativeCalculationType.equals("avgstde") || DataAggregation.RepresentativeCalculationType.equals("ww") ) ) {
			
			System.out.println("Error: unknown representative calculation type: " + DataAggregation.RepresentativeCalculationType);
			printParameterInfo();
			System.exit(-1);
		}

		if (DataAggregation.GroupSelection.equals("all") || DataAggregation.GroupSelection.equals("med-") || 
				DataAggregation.GroupSelection.equals("med+")) {
			
			// parameter OK
		} else if (isDouble(DataAggregation.GroupSelection)) {
			DataAggregation.GroupSelectionDistribution = Double.parseDouble(DataAggregation.GroupSelection);
			if (DataAggregation.GroupSelectionDistribution < 0 || 1.0 < DataAggregation.GroupSelectionDistribution) {
				System.out.println("Error: unknown selection of group: " + DataAggregation.GroupSelection);
				printParameterInfo();
				System.exit(-1);
			}
		} else {
			System.out.println("Error: unknown selection of group: " + DataAggregation.GroupSelection);
			printParameterInfo();
			System.exit(-1);
		}
	}
	
	/**
	 * print arg description for user
	 */
	public static void printParameterInfo() {
		System.out.println("");
		System.out.println("Parameter: ");
		System.out.println(" -i = Path of Inputfile (required & multi)");
		System.out.println(" -o = Path of Outputfile (required)");
		System.out.println(" -d = distance in meters to a new Cell with same Ch & SC (default: 5000)");
		System.out.println(" -pr = prefer Real Data at aggregation ([yes|no] default: yes)");
		System.out.println(" -g = selection of group (default: all");
		System.out.println("    all  = no selection, use all datas (-r & -t not needed)");
		System.out.println("    med- = median (less preferred)");
		System.out.println("    med+ = median (bigger preferred)");
		System.out.println("    0 - 1 = selection of representative: ");
		System.out.println("        0  = minimum ");
		System.out.println("        1 = maximum ");
		System.out.println(" -t = type of data (default: datarate) : ");
		System.out.println("    datarate / delay / lossrate");
		System.out.println(" -r = representative calculation type in group (default: ww): ");
		System.out.println("    min     = minimum");
		System.out.println("    max     = maximum");
		System.out.println("    avg     = arithmetic mean");
		System.out.println("    med     = median");
		System.out.println("    minstde = lower limit of the standard deviation");
		System.out.println("    maxstde = upper limit of the standard deviation");
		System.out.println("    avgstde = arithmetic mean in limits of the standard deviation");
		System.out.println("    ww      = way weighted");
		
		System.out.println("");
	}
	
	/**
	 * check if str ist double
	 */
	public static boolean isDouble(String str)  
	{
		try {
			@SuppressWarnings("unused")
			double d = Double.parseDouble(str);
		}
		catch(Exception e) {
			return false;  
		}  
		return true;  
	}
}
