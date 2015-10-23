
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

//this class represents one edge save all "Gruppen" for downstream and upstream datasets
public class DA_EdgeGroups {

    public long objID;
    public static long objCount = 0;

    public String edge_id_str = "";

    public Vector<Integer> Vector_matchedLinkNrGlobal_down = new Vector<Integer>();
    public TreeMap<Integer, DA_DatasetGroup> Groups_down = new TreeMap<Integer, DA_DatasetGroup>();

    public Vector<Integer> Vector_matchedLinkNrGlobal_up = new Vector<Integer>();
    public TreeMap<Integer, DA_DatasetGroup> Groups_up = new TreeMap<Integer, DA_DatasetGroup>();

    public DA_DatasetGroup SelectedDataAggregationGroup_down = null;

    public DA_DatasetGroup SelectedDataAggregationGroup_up = null;

    public static boolean preferReal = true;

    public DA_EdgeGroups() {
        this.objID = DA_EdgeGroups.objCount;
        DA_EdgeGroups.objCount++;
    }

    /**
     * start calculations of Repräsentanten for all Gruppen
     */
    public void aggregation() {
        for (Integer nr : Vector_matchedLinkNrGlobal_down) {
            DA_DatasetGroup dag = Groups_down.get(nr);
            dag.aggregation();
        }

        for (Integer nr : Vector_matchedLinkNrGlobal_up) {
            DA_DatasetGroup dag = Groups_up.get(nr);
            dag.aggregation();
        }

        SelectGroup();
    }

    /**
     * Gruppenauswahl: Select one Gruppe
     */
    public void SelectGroup() {

        Comparator<Double> comparator = new Comparator<Double>() {
            @Override
            public int compare(Double a, Double b) {
                if (a < b) {
                    return -1;
                } else {
                    return 1;
                }
            }
        };

        TreeMap<Double, DA_DatasetGroup> temp_Group_down = new TreeMap<Double, DA_DatasetGroup>(comparator);

        for (Integer nr : Vector_matchedLinkNrGlobal_down) {
            DA_DatasetGroup dag = Groups_down.get(nr);
            if (preferReal == false || dag.type.contains("Back") == false) {
                temp_Group_down.put(dag.RepresentativeValue, dag);
            }
        }

        if (preferReal && temp_Group_down.size() == 0) {
            for (Integer nr : Vector_matchedLinkNrGlobal_down) {
                DA_DatasetGroup dag = Groups_down.get(nr);
                temp_Group_down.put(dag.RepresentativeValue, dag);
            }
        }

        if (temp_Group_down.size() == 0) {
            temp_Group_down.clear();
        }

        if (DataAggregation.GroupSelection.startsWith("med")) {

            int med_index;
            if (temp_Group_down.size() % 2 == 1) {
                med_index = temp_Group_down.size() + 1;
                med_index = med_index / 2;
            } else {
                med_index = temp_Group_down.size() / 2;
                if (DataAggregation.GroupSelection.equals("med+")) {
                    med_index++;
                }
            }

            int i = 1;
            for (Map.Entry<Double, DA_DatasetGroup> entry : temp_Group_down.entrySet()) {
                if (i == med_index) {
                    SelectedDataAggregationGroup_down = entry.getValue();
                    break;
                }
                i++;
            }

        } else if (DataAggregation.GroupSelectionDistribution != -1) {

            if (DataAggregation.GroupSelectionDistribution == 0) {
                SelectedDataAggregationGroup_down = temp_Group_down.firstEntry().getValue();
            } else if (DataAggregation.GroupSelectionDistribution == 1.0) {
                SelectedDataAggregationGroup_down = temp_Group_down.lastEntry().getValue();
            } else {
                double min = temp_Group_down.firstEntry().getValue().RepresentativeValue;
                double max = temp_Group_down.lastEntry().getValue().RepresentativeValue;

                double m = DataAggregation.GroupSelectionDistribution;
                m = m * (max - min);
                m = min + m;

                min = Double.MAX_VALUE;

                for (Map.Entry<Double, DA_DatasetGroup> entry : temp_Group_down.entrySet()) {
                    double r = entry.getValue().RepresentativeValue;
                    double dif = m - r;
                    if (dif < 0) {
                        dif = dif * -1.0;
                    }

                    if (dif < min) {
                        SelectedDataAggregationGroup_down = entry.getValue();
                        min = dif;
                    }
                }
            }
        }

        if (this.edge_id_str.equals("1003089_1599954435_25948467_1599954435")) {
            this.edge_id_str = this.edge_id_str + "";
        }

        TreeMap<Double, DA_DatasetGroup> temp_Group_up = new TreeMap<Double, DA_DatasetGroup>(comparator);

        for (Integer nr : Vector_matchedLinkNrGlobal_up) {
            DA_DatasetGroup dag = Groups_up.get(nr);
            if (preferReal == false || dag.type.contains("Back") == false) {
                temp_Group_up.put(dag.RepresentativeValue, dag);
            }
        }

        if (preferReal && temp_Group_up.size() == 0) {
            for (Integer nr : Vector_matchedLinkNrGlobal_up) {
                DA_DatasetGroup dag = Groups_up.get(nr);
                temp_Group_up.put(dag.RepresentativeValue, dag);
            }
        }

        if (temp_Group_up.size() == 0) {
            temp_Group_up.clear();
        }

        if (DataAggregation.GroupSelection.startsWith("med")) {

            int med_index;
            if (temp_Group_up.size() % 2 == 1) {
                med_index = temp_Group_up.size() + 1;
                med_index = med_index / 2;
            } else {
                med_index = temp_Group_up.size() / 2;
                if (DataAggregation.GroupSelection.equals("med+")) {
                    med_index++;
                }
            }

            int i = 1;
            for (Map.Entry<Double, DA_DatasetGroup> entry : temp_Group_up.entrySet()) {
                if (i == med_index) {
                    SelectedDataAggregationGroup_up = entry.getValue();
                    break;
                }
                i++;
            }

        } else if (DataAggregation.GroupSelectionDistribution != -1) {

            if (DataAggregation.GroupSelectionDistribution == 0) {
                SelectedDataAggregationGroup_up = temp_Group_up.firstEntry().getValue();
            } else if (DataAggregation.GroupSelectionDistribution == 1.0) {
                SelectedDataAggregationGroup_up = temp_Group_up.lastEntry().getValue();
            } else {
                double min = temp_Group_up.firstEntry().getValue().RepresentativeValue;
                double max = temp_Group_up.lastEntry().getValue().RepresentativeValue;

                double m = DataAggregation.GroupSelectionDistribution;
                m = m * (max - min);
                m = min + m;

                min = Double.MAX_VALUE;

                for (Map.Entry<Double, DA_DatasetGroup> entry : temp_Group_up.entrySet()) {
                    double r = entry.getValue().RepresentativeValue;
                    double dif = m - r;
                    if (dif < 0) {
                        dif = dif * -1.0;
                    }

                    if (dif < min) {
                        SelectedDataAggregationGroup_up = entry.getValue();
                        min = dif;
                    }
                }
            }
        }

    }
}
