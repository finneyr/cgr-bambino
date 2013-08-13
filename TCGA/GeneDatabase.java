package TCGA;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;

public class GeneDatabase {
  private static GeneDatabase db = null;

  private ArrayList<GeneInfo> rows;
  private HashMap<String,GeneInfo> symbol2gene;

  private GeneDatabase() {
    // private, force factory access
    parse(); 
  }

  private void parse() {
    ClassLoader cl = this.getClass().getClassLoader();
    String resource = "TCGA/gene_info.tab.gz";
    // generated by lpgws501:~edmonson/bin/heatmap_db -generate-gene-map
      
    URL load_url = cl == null ?
      ClassLoader.getSystemResource(resource) : cl.getResource(resource);

    rows = new ArrayList<GeneInfo>();
    symbol2gene = new HashMap<String,GeneInfo>();
    if (load_url != null) {
      try {
	URLConnection urlc = load_url.openConnection();
	InputStream is = urlc.getInputStream();
	BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(is)));
	String line;
	while ((line = br.readLine()) != null) {
	  String[] things = line.split("\t");
	  GeneInfo gi = new GeneInfo();
	  gi.gene_id = Integer.parseInt(things[0]);
	  gi.symbol = things[1];
	  gi.description = things[2];

	  symbol2gene.put(gi.symbol, gi);

	  rows.add(gi);
	}
      } catch (Exception e) {
	System.err.println("ERROR:"+e);  // debug
      }
    }
  }

  public GeneInfo get_gene_by_symbol(String s) {
    return symbol2gene.get(s);
  }

  public ArrayList<GeneInfo> get_rows() {
    return rows;
  }

  public static GeneDatabase get_gene_database() {
    if (db == null) db = new GeneDatabase();
    // lazy instantiation
    return db;
  }

  public static void main (String[] argv) {
    GeneDatabase db = get_gene_database();
    
    for (GeneInfo gi : db.get_rows()) {
      //      System.err.println(gi.symbol);  // debug
    }
  }

}