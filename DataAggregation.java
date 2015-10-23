
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Vector;

public class DataAggregation {

    public HashMap<String, DA_EdgeGroups> EdgeGroups = new HashMap<String, DA_EdgeGroups>();
    public Vector<String> EdgeIds = new Vector<String>();

    public int countGroups = 0;
    public int countEdgeIds = 0;
    public int countDatasets = 0;

    public static String RepresentativeCalculationType = "ww";

    public static String GroupSelection = "all";

    public static double GroupSelectionDistribution = -1;

    public static String RepresentativeDataType = "datarate";

    /**
     * save datasets if aggregation was used
     *
     * @param outputFile path of file
     */
    public void writeOutput(String outputFile) {
        try {
            System.out.println("write  ... " + outputFile);

            BufferedWriter bWriter = new BufferedWriter(new FileWriter(outputFile));

            String a = "type,down_up,timestamp,"
                    + "matched_latitude,matched_longitude,unmatched_latitude,unmatched_longitude,"
                    + "startNode_id,endNode_id,edge_id_str,length_in_edge,length_of_edge,"
                    + "matched_distribution_in_WayPart,"
                    + "datarate,delay,loss_rate,matchedLinkNr,"
                    + "WCDMA_Ch,WCDMA_SC,GSM_CellId,GSM_LAC,CellId,matchedLinkNrGlobal";

            bWriter.write(a);
            bWriter.newLine();

            for (String key : EdgeIds) {
                DA_EdgeGroups gg = EdgeGroups.get(key);

                for (int j = 0; j < gg.SelectedDataAggregationGroup_down.Datasets.size(); j++) {
                    Dataset od = gg.SelectedDataAggregationGroup_down.Datasets.get(j);

                    bWriter.write(od.type + ",");
                    bWriter.write(od.down_up + ",");
                    bWriter.write(od.timestamp + ",");
                    bWriter.write(od.matched_latitude + ",");
                    bWriter.write(od.matched_longitude + ",");
                    bWriter.write(od.unmatched_latitude + ",");
                    bWriter.write(od.unmatched_longitude + ",");
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

                for (int j = 0; j < gg.SelectedDataAggregationGroup_up.Datasets.size(); j++) {
                    Dataset od = gg.SelectedDataAggregationGroup_up.Datasets.get(j);

                    bWriter.write(od.type + ",");
                    bWriter.write(od.down_up + ",");
                    bWriter.write(od.timestamp + ",");
                    bWriter.write(od.matched_latitude + ",");
                    bWriter.write(od.matched_longitude + ",");
                    bWriter.write(od.unmatched_latitude + ",");
                    bWriter.write(od.unmatched_longitude + ",");
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

    /**
     * save Overview with all Repräsentanten for all Gruppen
     *
     * @param outputFile path of file
     */
    public void writeSumToFile(String filePath) {

        try {
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(filePath));

            String a = "type,down_up,edge_id_str,matchedLinkNrGlobal,count,"
                    + "count_datarate,min_datarate,max_datarate,avg_datarate,stde_datarate,min_stde_datarate,max_stde_datarate,avg_stde_datarate,med_datarate,ww_datarate,"
                    + "count_delay,min_delay,max_delay,avg_delay,stde_delay,min_stde_delay,max_stde_delay,avg_stde_delay,med_datarate,ww_delay,"
                    + "count_loss_rate,min_loss_rate,max_loss_rate,avg_loss_rate,stde_loss_rate,min_stde_loss_rate,max_stde_loss_rate,avg_stde_loss_rate,med_datarate,ww_loss_rate";

            bWriter.write(a);
            bWriter.newLine();

            for (String key : EdgeIds) {
                DA_EdgeGroups gg = EdgeGroups.get(key);

                for (Integer nr : gg.Vector_matchedLinkNrGlobal_down) {
                    DA_DatasetGroup g = gg.Groups_down.get(nr);

                    bWriter.write(g.type + ",");
                    bWriter.write(g.down_up + ",");
                    bWriter.write(g.edge_id_str + ",");
                    bWriter.write(g.matchedLinkNrGlobal + ",");
                    bWriter.write(g.count + ",");

                    bWriter.write(g.count_datarate + ",");
                    bWriter.write(g.min_datarate + ",");
                    bWriter.write(g.max_datarate + ",");
                    bWriter.write(g.avg_datarate + ",");
                    bWriter.write(g.stde_datarate + ",");
                    bWriter.write(g.min_stde_datarate + ",");
                    bWriter.write(g.max_stde_datarate + ",");
                    bWriter.write(g.avg_stde_datarate + ",");
                    bWriter.write(g.med_datarate + ",");
                    bWriter.write(g.way_weight_datarate + ",");

                    bWriter.write(g.count_delay + ",");
                    bWriter.write(g.min_delay + ",");
                    bWriter.write(g.max_delay + ",");
                    bWriter.write(g.avg_delay + ",");
                    bWriter.write(g.stde_delay + ",");
                    bWriter.write(g.min_stde_delay + ",");
                    bWriter.write(g.max_stde_delay + ",");
                    bWriter.write(g.avg_stde_delay + ",");
                    bWriter.write(g.med_delay + ",");
                    bWriter.write(g.way_weight_delay + ",");

                    bWriter.write(g.count_loss_rate + ",");
                    bWriter.write(g.min_loss_rate + ",");
                    bWriter.write(g.max_loss_rate + ",");
                    bWriter.write(g.avg_loss_rate + ",");
                    bWriter.write(g.stde_loss_rate + ",");
                    bWriter.write(g.min_stde_loss_rate + ",");
                    bWriter.write(g.max_stde_loss_rate + ",");
                    bWriter.write(g.avg_stde_loss_rate + ",");
                    bWriter.write(g.med_loss_rate + ",");
                    bWriter.write(g.way_weight_loss_rate + "");

                    bWriter.newLine();
                }

                for (Integer s : gg.Vector_matchedLinkNrGlobal_up) {
                    DA_DatasetGroup g = gg.Groups_up.get(s);

                    bWriter.write(g.type + ",");
                    bWriter.write(g.down_up + ",");
                    bWriter.write(g.edge_id_str + ",");
                    bWriter.write(g.matchedLinkNrGlobal + ",");
                    bWriter.write(g.count + ",");

                    bWriter.write(g.count_datarate + ",");
                    bWriter.write(g.min_datarate + ",");
                    bWriter.write(g.max_datarate + ",");
                    bWriter.write(g.avg_datarate + ",");
                    bWriter.write(g.stde_datarate + ",");
                    bWriter.write(g.min_stde_datarate + ",");
                    bWriter.write(g.max_stde_datarate + ",");
                    bWriter.write(g.avg_stde_datarate + ",");
                    bWriter.write(g.med_datarate + ",");
                    bWriter.write(g.way_weight_datarate + ",");

                    bWriter.write(g.count_delay + ",");
                    bWriter.write(g.min_delay + ",");
                    bWriter.write(g.max_delay + ",");
                    bWriter.write(g.avg_delay + ",");
                    bWriter.write(g.stde_delay + ",");
                    bWriter.write(g.min_stde_delay + ",");
                    bWriter.write(g.max_stde_delay + ",");
                    bWriter.write(g.avg_stde_delay + ",");
                    bWriter.write(g.med_delay + ",");
                    bWriter.write(g.way_weight_delay + ",");

                    bWriter.write(g.count_loss_rate + ",");
                    bWriter.write(g.min_loss_rate + ",");
                    bWriter.write(g.max_loss_rate + ",");
                    bWriter.write(g.avg_loss_rate + ",");
                    bWriter.write(g.stde_loss_rate + ",");
                    bWriter.write(g.min_stde_loss_rate + ",");
                    bWriter.write(g.max_stde_loss_rate + ",");
                    bWriter.write(g.avg_stde_loss_rate + ",");
                    bWriter.write(g.med_loss_rate + ",");
                    bWriter.write(g.way_weight_loss_rate + "");

                    bWriter.newLine();
                }
            }

            bWriter.close();
        } catch (Exception e) {
            System.out.println("Error: writeToFile: \n" + e.toString());
            System.exit(-1);
        }
    }

    /**
     * start calculations of Repräsentanten for all edges and all Gruppen
     */
    public void aggregation() {
        for (String EdgeId : EdgeIds) {
            DA_EdgeGroups gg = EdgeGroups.get(EdgeId);
            gg.aggregation();
        }
    }

    /**
     * add a dataset to edge / Gruppe Start of sorting: edge --> Gruppe -->
     * down/up
     *
     * @param ds
     */
    public void addDataset(Dataset ds) {
        DA_EdgeGroups gg = this.getGroupOfGroup(ds.edge_id_str);
        DA_DatasetGroup g = getGroup(ds, gg);
        g.Datasets.add(ds);
        countDatasets++;
    }

    /**
     * @param s edge id
     * @return edge with all Gruppen
     */
    private DA_EdgeGroups getGroupOfGroup(String s) {
        if (EdgeGroups.containsKey(s) == false) {
            DA_EdgeGroups gg = new DA_EdgeGroups();
            gg.edge_id_str = s;
            EdgeGroups.put(s, gg);
            EdgeIds.add(s);
            countEdgeIds++;
        }
        return EdgeGroups.get(s);
    }

    /**
     * @param ds
     * @param gg
     * @return existing or created Gruppe for ds
     */
    private DA_DatasetGroup getGroup(Dataset ds, DA_EdgeGroups gg) {
        if (ds.down_up.equals("down")) {
            if (gg.Groups_down.containsKey(ds.matchedLinkNrGlobal) == false) {
                DA_DatasetGroup g = new DA_DatasetGroup(ds);
                gg.Groups_down.put(ds.matchedLinkNrGlobal, g);
                gg.Vector_matchedLinkNrGlobal_down.add(ds.matchedLinkNrGlobal);

                countGroups++;
            }
            return gg.Groups_down.get(ds.matchedLinkNrGlobal);
        } else {
            if (gg.Groups_up.containsKey(ds.matchedLinkNrGlobal) == false) {
                DA_DatasetGroup g = new DA_DatasetGroup(ds);
                gg.Groups_up.put(ds.matchedLinkNrGlobal, g);
                gg.Vector_matchedLinkNrGlobal_up.add(ds.matchedLinkNrGlobal);

                countGroups++;
            }
            return gg.Groups_up.get(ds.matchedLinkNrGlobal);
        }
    }

}
