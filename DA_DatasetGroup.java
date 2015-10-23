import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

// this class represents one "Gruppe" 

public class DA_DatasetGroup {
	public long objID;
	public static long objCount = 0;
	
	public static final int DEFAULT_VALUE = -2;
	
	public String type = "";
	public String down_up = "";
	public String edge_id_str = "";
	public int matchedLinkNrGlobal = 0;
	public Vector<Dataset> Datasets = new Vector<Dataset>();
	
	public int min_datarate = DEFAULT_VALUE;
	public double min_delay = DEFAULT_VALUE;
	public double min_loss_rate = DEFAULT_VALUE;

	public int max_datarate = DEFAULT_VALUE;
	public double max_delay = DEFAULT_VALUE;
	public double max_loss_rate = DEFAULT_VALUE;

	// arithmetisches Mittel 
	public double avg_datarate = DEFAULT_VALUE;
	public double avg_delay = DEFAULT_VALUE;
	public double avg_loss_rate = DEFAULT_VALUE;

	// Standardabweichung (standard deviation)
	public double stde_datarate = DEFAULT_VALUE;
	public double stde_delay = DEFAULT_VALUE;
	public double stde_loss_rate = DEFAULT_VALUE;

	// arithmetisches Mittel nur innerhalb der Standardabweichung (standard deviation) (stde)
	public double avg_stde_datarate = DEFAULT_VALUE;
	public double avg_stde_delay = DEFAULT_VALUE;
	public double avg_stde_loss_rate = DEFAULT_VALUE;

	// Median
	public double med_datarate = DEFAULT_VALUE;
	public double med_delay = DEFAULT_VALUE;
	public double med_loss_rate = DEFAULT_VALUE;

	// untere Grenze der Standardabweichung
	public double min_stde_datarate = DEFAULT_VALUE;
	public double min_stde_delay = DEFAULT_VALUE;
	public double min_stde_loss_rate = DEFAULT_VALUE;

	// obere Grenze der Standardabweichung
	public double max_stde_datarate = DEFAULT_VALUE;
	public double max_stde_delay = DEFAULT_VALUE;
	public double max_stde_loss_rate = DEFAULT_VALUE;

	public double way_weight_datarate = DEFAULT_VALUE;
	public double way_weight_delay = DEFAULT_VALUE;
	public double way_weight_loss_rate = DEFAULT_VALUE;

	public int count = 0;
	public int count_datarate = 0;
	public int count_delay = 0;
	public int count_loss_rate = 0;

	public double RepresentativeValue = DEFAULT_VALUE;

	/**
	 * constructor 
	 * 
	 * @param ds first dataset in this "Gruppe"
	 */
	public DA_DatasetGroup(Dataset ds) {
		this.objID = DA_DatasetGroup.objCount;
		DA_DatasetGroup.objCount++;
		
		type = ds.type;
		down_up = ds.down_up;
		edge_id_str = ds.edge_id_str;
		matchedLinkNrGlobal = ds.matchedLinkNrGlobal;
	}
	
	/**
	 * remove all datasets with type "Start" and "Ende"
	 * if Gruppe has datasets with the Real or BackD.
	 */
	public void filterType() {
		boolean hasReal = false;
		
		for(Dataset ds : Datasets) {
			if (ds.type.startsWith("Start") == false && ds.type.startsWith("End") == false) {
				hasReal = true;
				break;
			}
		}
		
		if (hasReal) {
			for (int i = Datasets.size() - 1; i >= 0; i--) {
				if (Datasets.get(i).type.startsWith("Start")  || Datasets.get(i).type.startsWith("End") ) {
					Datasets.remove(i);
				}
			}		
		}
		
		type = Datasets.firstElement().type;
	}
	
	/**
	 * start all calculations of Repräsentanten
	 */
	public void aggregation() {
		filterType();
		setCount();
		setMin();
		setMax();
		setAvg();
		setStDe();
		setMinStde();
		setMaxStde();
		setAvgStde();
		setMed();
		setWayWeight();
		
		if (DataAggregation.RepresentativeCalculationType.equals("min")) {
			if (DataAggregation.RepresentativeDataType.equals("datarate")) {
				RepresentativeValue = min_datarate;
			} else if (DataAggregation.RepresentativeDataType.equals("delay")) {
				RepresentativeValue = min_delay;
			} else if (DataAggregation.RepresentativeDataType.equals("lossrate")) {
				RepresentativeValue = min_loss_rate;
			}
		} else if (DataAggregation.RepresentativeCalculationType.equals("max")) {
			if (DataAggregation.RepresentativeDataType.equals("datarate")) {
				RepresentativeValue = max_datarate;
			} else if (DataAggregation.RepresentativeDataType.equals("delay")) {
				RepresentativeValue = max_delay;
			} else if (DataAggregation.RepresentativeDataType.equals("lossrate")) {
				RepresentativeValue = max_loss_rate;
			}
		} else if (DataAggregation.RepresentativeCalculationType.equals("avg")) {
			if (DataAggregation.RepresentativeDataType.equals("datarate")) {
				RepresentativeValue = avg_datarate;
			} else if (DataAggregation.RepresentativeDataType.equals("delay")) {
				RepresentativeValue = avg_delay;
			} else if (DataAggregation.RepresentativeDataType.equals("lossrate")) {
				RepresentativeValue = avg_loss_rate;
			}
		} else if (DataAggregation.RepresentativeCalculationType.equals("med")) {
			if (DataAggregation.RepresentativeDataType.equals("datarate")) {
				RepresentativeValue = med_datarate;
			} else if (DataAggregation.RepresentativeDataType.equals("delay")) {
				RepresentativeValue = med_delay;
			} else if (DataAggregation.RepresentativeDataType.equals("lossrate")) {
				RepresentativeValue = med_loss_rate;
			}
		} else if (DataAggregation.RepresentativeCalculationType.equals("minstde")) {
			if (DataAggregation.RepresentativeDataType.equals("datarate")) {
				RepresentativeValue = min_stde_datarate;
			} else if (DataAggregation.RepresentativeDataType.equals("delay")) {
				RepresentativeValue = min_stde_delay;
			} else if (DataAggregation.RepresentativeDataType.equals("lossrate")) {
				RepresentativeValue = min_stde_loss_rate;
			}
		} else if (DataAggregation.RepresentativeCalculationType.equals("maxstde")) {
			if (DataAggregation.RepresentativeDataType.equals("datarate")) {
				RepresentativeValue = max_stde_datarate;
			} else if (DataAggregation.RepresentativeDataType.equals("delay")) {
				RepresentativeValue = max_stde_delay;
			} else if (DataAggregation.RepresentativeDataType.equals("lossrate")) {
				RepresentativeValue = max_stde_loss_rate;
			}
		} else if (DataAggregation.RepresentativeCalculationType.equals("avgstde")) {
			if (DataAggregation.RepresentativeDataType.equals("datarate")) {
				RepresentativeValue = avg_datarate;
			} else if (DataAggregation.RepresentativeDataType.equals("delay")) {
				RepresentativeValue = avg_delay;
			} else if (DataAggregation.RepresentativeDataType.equals("lossrate")) {
				RepresentativeValue = avg_loss_rate;
			}
		} else if (DataAggregation.RepresentativeCalculationType.equals("ww")) {
			if (DataAggregation.RepresentativeDataType.equals("datarate")) {
				RepresentativeValue = way_weight_datarate;
			} else if (DataAggregation.RepresentativeDataType.equals("delay")) {
				RepresentativeValue = way_weight_delay;
			} else if (DataAggregation.RepresentativeDataType.equals("lossrate")) {
				RepresentativeValue = way_weight_loss_rate;
			}
		}
	}
	
	/**
	 * calculate Repräsentant Weg-Gewichtet
	 */
	public void setWayWeight() {
		
		Vector<Dataset> temp_Datasets = new Vector<Dataset>();
		for (Dataset ds : Datasets) {
			if (ds.datarate != -1) {
				temp_Datasets.add(ds);
			}
		}

		if (temp_Datasets.size() > 1) {
			double [] datarate = new double[temp_Datasets.size()];

			double lg = 0;
			double rg;
			double m;

			for (int i = 0; i < temp_Datasets.size() - 1; i++) {
				Dataset dsl = temp_Datasets.get(i);
				Dataset dsr = temp_Datasets.get(i + 1);

				rg = dsr.matched_distribution_in_WayPart - dsl.matched_distribution_in_WayPart;
				rg = rg / 2;
				rg = rg + dsl.matched_distribution_in_WayPart;
				m = rg - lg;

				datarate[i] = dsl.datarate * m;

				lg = rg;
			}

			m = 1.0 - lg;

			int i = temp_Datasets.size() - 1;
			datarate[i] = temp_Datasets.lastElement().datarate * m;

			way_weight_datarate = 0;
			for (i = 0; i < temp_Datasets.size(); i++) {
				way_weight_datarate += datarate[i];
			}
		} else {
			if (temp_Datasets.size() == 1) {
				way_weight_datarate = temp_Datasets.firstElement().datarate;
			}
		}

		
		temp_Datasets.clear();
		for (Dataset ds : Datasets) {
			if (ds.delay != -1) {
				temp_Datasets.add(ds);
			}
		}

		if (temp_Datasets.size() > 1) {
			double [] delay = new double[temp_Datasets.size()];

			double lg = 0;
			double rg;
			double m;

			for (int i = 0; i < temp_Datasets.size() - 1; i++) {
				Dataset dsl = temp_Datasets.get(i);
				Dataset dsr = temp_Datasets.get(i + 1);

				rg = dsr.matched_distribution_in_WayPart - dsl.matched_distribution_in_WayPart;
				rg = rg / 2;
				rg = rg + dsl.matched_distribution_in_WayPart;
				m = rg - lg;

				delay[i] = dsl.delay * m;

				lg = rg;
			}

			m = 1.0 - lg;

			int i = temp_Datasets.size() - 1;
			delay[i] = temp_Datasets.lastElement().delay * m;

			way_weight_delay = 0;
			for (i = 0; i < temp_Datasets.size(); i++) {
				way_weight_delay += delay[i];
			}
		} else {
			if (temp_Datasets.size() == 1) {
				way_weight_delay = temp_Datasets.firstElement().delay;
			}
		}

		
		temp_Datasets.clear();
		for (Dataset ds : Datasets) {
			if (ds.loss_rate != -1) {
				temp_Datasets.add(ds);
			}
		}

		if (temp_Datasets.size() > 1) {
			double [] loss_rate = new double[temp_Datasets.size()];
			
			double lg = 0;
			double rg;
			double m;
			
			for (int i = 0; i < temp_Datasets.size() - 1; i++) {
				Dataset dsl = temp_Datasets.get(i);
				Dataset dsr = temp_Datasets.get(i + 1);
				
				rg = dsr.matched_distribution_in_WayPart - dsl.matched_distribution_in_WayPart;
				rg = rg / 2;
				rg = rg + dsl.matched_distribution_in_WayPart;
				m = rg - lg;
				
				loss_rate[i] = dsl.loss_rate * m;
				
				lg = rg;
			}
			
			m = 1.0 - lg;
			
			int i = temp_Datasets.size() - 1;
			loss_rate[i] = temp_Datasets.lastElement().loss_rate * m;
			
			way_weight_loss_rate = 0;
			for (i = 0; i < temp_Datasets.size(); i++) {
				way_weight_loss_rate += loss_rate[i];
			}
		} else {
			if (temp_Datasets.size() == 1) {
				way_weight_loss_rate = temp_Datasets.firstElement().loss_rate;
			}
		}
	}
	
	/**
	 * check and set counts of datasets
	 */
	public void setCount() {		
		for (int i = 0; i < Datasets.size(); i++) {
			Dataset ds = Datasets.get(i);
			if (ds.datarate != -1) {count_datarate++;}
			if (ds.delay != -1) {count_delay++;	}
			if (ds.loss_rate != -1) {count_loss_rate++;}
		}
		count = Datasets.size();
	}
	
	/**
	 * calculate Repräsentant min
	 */
	public void setMin() {
		if (Datasets.size() != 0) {
			min_datarate = Integer.MAX_VALUE;
			min_delay = Double.MAX_VALUE;
			min_loss_rate = Double.MAX_VALUE;
			
			for (int i = 0; i < Datasets.size(); i++) {
				Dataset od = Datasets.get(i);
				
				if (od.datarate < min_datarate && od.datarate != -1) {min_datarate = od.datarate;}
				if (od.delay < min_delay && od.delay != -1) {min_delay = od.delay;}
				if (od.loss_rate < min_loss_rate && od.loss_rate != -1) {min_loss_rate = od.loss_rate;}
			}
			
			if (min_datarate == Integer.MAX_VALUE) {min_datarate = DEFAULT_VALUE;}
			if (min_delay == Double.MAX_VALUE) {min_delay = DEFAULT_VALUE;}
			if (min_loss_rate == Double.MAX_VALUE) {min_loss_rate = DEFAULT_VALUE;}
		}
	}

	/**
	 * calculate Repräsentant max
	 */
	public void setMax() {
		if (Datasets.size() != 0) {
			max_datarate = Integer.MIN_VALUE;
			max_delay = -Double.MAX_VALUE;
			max_loss_rate = -Double.MAX_VALUE;

			for (int i = 0; i < Datasets.size(); i++) {
				Dataset od = Datasets.get(i);
				
				if (od.datarate > max_datarate && od.datarate != -1) {
					max_datarate = od.datarate;
				}
				if (od.delay > max_delay && od.delay != -1) {
					max_delay = od.delay;
				}
				if (od.loss_rate > max_loss_rate && od.loss_rate != -1) {
					max_loss_rate = od.loss_rate;
				}
			}
			
			if (max_datarate == Integer.MIN_VALUE) {max_datarate = DEFAULT_VALUE;}
			if (max_delay == -Double.MAX_VALUE) {max_delay = DEFAULT_VALUE;}
			if (max_loss_rate == -Double.MAX_VALUE) {max_loss_rate = DEFAULT_VALUE;}
		}
	}
	
	/**
	 * calculate Repräsentant arithmetische Mittel
	 */
	public void setAvg() {
		if (Datasets.size() != 0) {
			avg_datarate = 0;
			avg_delay = 0;
			avg_loss_rate = 0;

			double count_datarate = 0;
			double count_delay = 0;
			double count_loss_rate = 0;
			
			for (int i = 0; i < Datasets.size(); i++) {
				Dataset od = Datasets.get(i);

				if (od.datarate != -1) {
					avg_datarate += od.datarate;
					count_datarate++;
				}
				if (od.delay != -1) {
					avg_delay += od.delay;
					count_delay++;
				}
				if (od.loss_rate != -1) {
					avg_loss_rate += od.loss_rate;
					count_loss_rate++;
				}
			}

			if (count_datarate != 0) {
				avg_datarate = avg_datarate / count_datarate;				
			} else {
				avg_datarate = DEFAULT_VALUE;
			}
			if (count_delay != 0) {
				avg_delay = avg_delay / count_delay;			
			} else {
				avg_delay = DEFAULT_VALUE;
			}
			if (count_loss_rate != 0) {
				avg_loss_rate = avg_loss_rate / count_loss_rate;	
			} else {
				avg_loss_rate = DEFAULT_VALUE;
			}
		}
	}

	/**
	 * calculate Standardabweichung
	 */
	public void setStDe() {
		if (avg_datarate == DEFAULT_VALUE) {
			setAvg();
		}
		
		
		if (Datasets.size() != 0) {
			stde_datarate = 0;
			stde_delay = 0;
			stde_loss_rate = 0;

			double d_count_datarate = 0;
			double d_count_delay = 0;
			double d_count_loss_rate = 0;
			
			for (int i = 0; i < Datasets.size(); i++) {
				Dataset od = Datasets.get(i);

				if (od.datarate != -1) {
					stde_datarate += ((od.datarate - avg_datarate)  * (od.datarate - avg_datarate));
					d_count_datarate++;
				}
				if (od.delay != -1) {
					stde_delay += ((od.delay - avg_delay) * (od.delay - avg_delay));
					d_count_delay++;
				}
				if (od.loss_rate != -1) {
					stde_loss_rate += ((od.loss_rate - avg_loss_rate) * (od.loss_rate - avg_loss_rate));
					d_count_loss_rate++;
				}
			}

			if (d_count_datarate != 0 && avg_datarate >= 0) {
				stde_datarate = stde_datarate / d_count_datarate;
				stde_datarate = Math.sqrt(stde_datarate);				
			} else {
				stde_datarate = DEFAULT_VALUE;
			}
			if (d_count_delay != 0 && avg_delay >= 0) {
				stde_delay = stde_delay / d_count_delay;
				stde_delay = Math.sqrt(stde_delay);			
			} else {
				stde_delay = DEFAULT_VALUE;
			}
			if (d_count_loss_rate != 0 && avg_loss_rate >= 0) {
				stde_loss_rate = stde_loss_rate / d_count_loss_rate;
				stde_loss_rate = Math.sqrt(stde_loss_rate);
			} else {
				stde_loss_rate = DEFAULT_VALUE;
			}
		}
	}
	
	/**
	 * calculate Repräsentant min_stde
	 */
	public void setMinStde() {
		if (avg_datarate == DEFAULT_VALUE) {
			setAvg();
		}
		if (stde_datarate == DEFAULT_VALUE) {
			setStDe();
		}
		if (min_datarate == DEFAULT_VALUE) {
			setMin();
		}

		if (avg_datarate != DEFAULT_VALUE && stde_datarate != DEFAULT_VALUE) {
			min_stde_datarate = avg_datarate - stde_datarate;
			if (min_stde_datarate < min_datarate) {
				min_stde_datarate = min_datarate;
			}
		}
		if (avg_delay != DEFAULT_VALUE && stde_delay != DEFAULT_VALUE) {
			min_stde_delay = avg_delay - stde_delay;
			if (min_stde_delay < min_delay) {
				min_stde_delay = min_delay;
			}
		}
		if (avg_loss_rate != DEFAULT_VALUE && stde_loss_rate != DEFAULT_VALUE) {
			min_stde_loss_rate = avg_loss_rate - stde_loss_rate;
			if (min_stde_loss_rate < min_loss_rate) {
				min_stde_loss_rate = min_loss_rate;
			}
		}
	}

	/**
	 * calculate Repräsentant max_stde
	 */
	public void setMaxStde() {
		if (avg_datarate == DEFAULT_VALUE) {
			setAvg();
		}
		if (stde_datarate == DEFAULT_VALUE) {
			setStDe();
		}
		if (max_datarate == DEFAULT_VALUE) {
			setMax();
		}

		if (avg_datarate != DEFAULT_VALUE && stde_datarate != DEFAULT_VALUE) {
			max_stde_datarate = avg_datarate + stde_datarate;
			if (max_stde_datarate > max_datarate) {
				max_stde_datarate = max_datarate;
			}
		}
		if (avg_delay != DEFAULT_VALUE && stde_delay != DEFAULT_VALUE) {
			max_stde_delay = avg_delay + stde_delay;
			if (max_stde_delay > max_delay) {
				max_stde_delay = max_delay;
			}
		}
		if (avg_loss_rate != DEFAULT_VALUE && stde_loss_rate != DEFAULT_VALUE) {
			max_stde_loss_rate = avg_loss_rate + stde_loss_rate;
			if (max_stde_loss_rate > max_loss_rate) {
				max_stde_loss_rate = max_loss_rate;
			}
		}
	}

	/**
	 * calculate Repräsentant arithmetische Mittel between min_stde and max_stde
	 */
	public void setAvgStde() {
		if (Datasets.size() != 0) {

			if (min_stde_datarate == DEFAULT_VALUE) {
				setMinStde();
			}
			if (max_stde_datarate == DEFAULT_VALUE) {
				setMaxStde();
			}

			avg_stde_datarate = 0;
			avg_stde_delay = 0;
			avg_stde_loss_rate = 0;

			double d_count_datarate = 0;
			double d_count_delay = 0;
			double d_count_loss_rate = 0;

			for (int i = 0; i < Datasets.size(); i++) {
				Dataset od = Datasets.get(i);

				if (od.datarate != -1 && min_stde_datarate <= od.datarate && od.datarate <= max_stde_datarate) {
					avg_stde_datarate += od.datarate;
					d_count_datarate++;
				}
				if (od.delay != -1 && min_stde_delay <= od.delay && od.delay <= max_stde_delay) {
					avg_stde_delay += od.delay;
					d_count_delay++;
				}
				if (od.loss_rate != -1 && min_stde_loss_rate <= od.loss_rate && od.loss_rate <= max_stde_loss_rate) {
					avg_stde_loss_rate += od.loss_rate;
					d_count_loss_rate++;
				}
			}

			if (d_count_datarate != 0) {
				avg_stde_datarate = avg_stde_datarate / d_count_datarate;				
			} else {
				avg_stde_datarate = DEFAULT_VALUE;
			}
			if (d_count_delay != 0) {
				avg_stde_delay = avg_stde_delay / d_count_delay;
			} else {
				avg_stde_delay = DEFAULT_VALUE;
			}
			if (d_count_loss_rate != 0) {
				avg_stde_loss_rate = avg_stde_loss_rate / d_count_loss_rate;
			} else {
				avg_stde_loss_rate = DEFAULT_VALUE;
			}
		}
	}

	/**
	 * calculate Repräsentant Median
	 */
	public void setMed() {
		
		if (Datasets.size() != 0) {
		
			Comparator<Double> comparator = new Comparator<Double>() {
	            @Override
	            public int compare(Double a, Double b) {
	                if ( a < b) {
	                	return -1;
	                } else {
	                	return 1;
	                }
	            }
	        };
			
			TreeSet<Double> ts_datarate = new TreeSet<Double>(comparator);
			TreeSet<Double> ts_delay = new TreeSet<Double>(comparator);
			TreeSet<Double> ts_loss_rate = new TreeSet<Double>(comparator);
			
			for (int i = 0; i < Datasets.size(); i++) {
				Dataset od = Datasets.get(i);
			
				if (od.datarate != -1) {ts_datarate.add(new Double(od.datarate));}
				if (od.delay != -1) {ts_delay.add(new Double(od.delay));}
				if (od.loss_rate != -1) {ts_loss_rate.add(new Double(od.loss_rate));}
			}
			
			int m;
			
			if (ts_datarate.size() % 2 == 1) {
				m = (ts_datarate.size() + 1) / 2;
			} else {
				m = ts_datarate.size() / 2;
			}
			
			Iterator<Double> itr = ts_datarate.iterator();
			int i = 1;
			while(itr.hasNext()){
			    Double d = itr.next();
			    if (i == m) {
			    	if (ts_datarate.size() % 2 == 1) {
			    		med_datarate = d;
			    		break;
			    	} else {
			    		med_datarate = d;
			    		d = itr.next();
			    		med_datarate += d;
			    		med_datarate /= 2.0;
			    		break;
			    	}
			    }
			    i++;
			}

			
			if (ts_delay.size() % 2 == 1) {
				m = (ts_delay.size() + 1) / 2;
			} else {
				m = ts_delay.size() / 2;
			}

			itr = ts_delay.iterator();
			i = 1;
			while(itr.hasNext()){
			    Double d = itr.next();
			    if (i == m) {
			    	if (ts_delay.size() % 2 == 1) {
			    		med_delay = d;
			    		break;
			    	} else {
			    		med_delay = d;
			    		d = itr.next();
			    		med_delay += d;
			    		med_delay /= 2.0;
			    		break;
			    	}
			    }
			    i++;
			}
			
			
			if (ts_loss_rate.size() % 2 == 1) {
				m = (ts_loss_rate.size() + 1) / 2;
			} else {
				m = ts_loss_rate.size() / 2;
			}
			
			itr = ts_loss_rate.iterator();
			i = 1;
			while(itr.hasNext()){
			    Double d = itr.next();
			    if (i == m) {
			    	if (ts_loss_rate.size() % 2 == 1) {
			    		med_loss_rate = d;
			    		break;
			    	} else {
			    		med_loss_rate = d;
			    		d = itr.next();
			    		med_loss_rate += d;
			    		med_loss_rate /= 2.0;
			    		break;
			    	}
			    }
			    i++;
			}
		}
	}
}


