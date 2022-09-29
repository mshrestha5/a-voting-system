package Voters;
import java.io.*;
import java.util.Scanner;
import java.util.Random;
public class VoterList {
	
	
	private static Integer VoterID = 0;
	
	public static void fileCreation() throws Exception {
		File file = new File("VoterList.txt");
		if (file.exists()) {
		}
		else {
			System.out.println("File does not exist. Make file (y or n)?");
			Scanner answer = new Scanner(System.in);
			if (answer.next().compareToIgnoreCase("y") == 0) {
				PrintWriter create = new PrintWriter(file);
				create.println("Name:" + "  " + "Address:" + "  " + "VoterID:" + "  " + "Voted(P)?:" + "  " + "Voted(VP)?:" + "  " + "Voted(S)?:" + "  " + "Voted(H)?:");
				create.close();
			}
			else {
				System.out.print("Operation aborted.");
				System.exit(1);
			}
		}
	}
	
	public static void checkExistingName(String n, String a1, String a2) throws Exception {
		
		File file = new File("VoterList.txt"); 
		String[] words = null;  
		FileReader fr = new FileReader(file);  
		BufferedReader br = new BufferedReader(fr); 
		String s;  
		String info = n + "  " + a1 + "  " + a2;   
		while((s = br.readLine()) != null){
			words = s.split("  ");  
		    for (String word : words){
		    	if (word.equals(n)){
		    		System.out.println("Matching entry found: " + word);
		    		System.out.println("\nUser input: ");
		    		System.out.println(info);
		    		System.out.println("\nData bank info: ");
		    		String wordsJoin = words[0] + "  " + words[1] + "  " + words[2];
		    		System.out.println(wordsJoin);
		    		System.out.println("\nUpdate existing info? (y or n): ");
		    		Scanner input = new Scanner(System.in);
		    		if (input.next().compareToIgnoreCase("y") == 0) {
		    			System.out.println("Please enter VoterID: ");
		    			if (input.next().equals(words[3])) {
		    				System.out.println(info + " \t\treplaces\t " + wordsJoin);
		    				System.out.println("Is this correct (y or n)?");
		    				if (input.next().compareToIgnoreCase("y") == 0) {
		    					String in = null;
		    				    Scanner sc = new Scanner(file);
		    				    StringBuffer sb = new StringBuffer();
		    				    while (sc.hasNextLine()) {
		    				    	in = sc.nextLine() + "\n";
		    				        sb.append(in);
		    				    }
		    				    String result = sb.toString();
		    				    //System.out.println("Contents of the file before change: "+result);
		    				    result = result.replaceAll(wordsJoin, info);  
		    				    PrintWriter writer = new PrintWriter(file);
		    				    writer.append(result);
		    				    //System.out.println("Contents of the file after change: "+result);
		    				    writer.flush();
		    				    writer.close();
		    				    System.exit(1);
		    				}	
		    				else {
		    					System.out.println("Session complete. Returning to start.");
				    			System.exit(1);
		    				}
		    			}
		    			else {
		    				System.out.println("Invalid entry. Try again (y or n)?");
		    				if (input.next().compareToIgnoreCase("y") == 0) {
		    					checkExistingName(n, a1, a2);
		    				}else {System.out.println("End of execution.");System.exit(1);}
		    			}
		    		}else {System.out.println("Session complete. End of execution."); System.exit(1);}
		    	}
		    }
		} 
		System.out.println("No match found. Generating VoterID.");
		String ID = getVoterID().toString();
		addVoter(ID, info);
		fr.close();	
	}
	
	public static String addVoter(String ID, String info)  throws IOException {
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter("VoterList.txt", true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.println(info + "  " + ID + "  " + "NO" + "  " + "NO" + "  " + "NO" + "  " + "NO");
			pw.flush();
		} finally {
			try {
				pw.close();
				bw.close();
				fw.close();
			} catch (IOException io) {}
		}
		return ID;	
	}
	
	public static Integer displayID(Integer ID) {
		System.out.println("Your VoterID is: " + ID);
		return ID;
	}
	
	

	public static void setVoterID() {
		
		Random num = new Random();
		int low = 100000000;
		int high = 999999999;
		VoterID = num.nextInt(high-low) + low;
		
	}
	
	
	public static String getVoterID() {
		setVoterID();
		return VoterID.toString();
		
	}
	
	
	public static void castVote(String ID, String pVote, String vpVote, String sVote, String hVote) {
		
			System.out.println("Please select position (President, Vice President, Senate, or House): ");
			Scanner input = new Scanner(System.in);
			String in = input.nextLine();
			String office;
			switch (in) {
				case "President":
					if(pVote.equalsIgnoreCase("NO")) {
						office = "p";
						CandidateList.castVote(office);		
						try {
							voteToYes(ID,office);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
						
					}else {
						System.out.println("You've already voted for this position. Please select another position.");
						castVote(ID, pVote, vpVote, sVote, hVote);
					}
				case "Vice President":
					if(vpVote.equalsIgnoreCase("NO")) {
						office = "vp";
						CandidateList.castVote(office);		
						try {
							voteToYes(ID,office);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}else {
						System.out.println("You've already voted for this position. Please select another position.");
						castVote(ID, pVote, vpVote, sVote, hVote);
					}
				case "Senate":
					if(sVote.equalsIgnoreCase("NO")) {
						office = "s";
						CandidateList.castVote(office);		
						try {
							voteToYes(ID,office);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}else {
						System.out.println("You've already voted for this position. Please select another position.");
						castVote(ID, pVote, vpVote, sVote, hVote);
					}
				case "House":
					if(hVote.equalsIgnoreCase("NO")) {
						office = "h";
						CandidateList.castVote(office);
						try {
							voteToYes(ID,office);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}else {
						System.out.println("You've already voted for this position. Please select another position.");
						castVote(ID, pVote, vpVote, sVote, hVote);
					}
				default :
					System.out.println("Invalid selection. Try again.");
					castVote(ID, pVote, vpVote, sVote, hVote);
			}			
	}
	
	public static void voteToYes(String ID, String office) throws Exception {
		
		File file = new File("VoterList.txt"); 
		String[] words = null;  
		FileReader fr = new FileReader(file);  
		BufferedReader br = new BufferedReader(fr); 
		String s; 
		String wordsJoin = "";
		String updateVote = "";
		while((s = br.readLine()) != null){
			words = s.split("  ");  
		    for (String word : words){
		    	if (word.equals(ID)){
		    		//System.out.println("Matching entry found: " + word);
		    		//System.out.println("Updating vote to 'YES'.");
		    		if (office == "p") {
		    			wordsJoin = words[0] + "  " + words[1] + "  " + words[2] + "  " + words[3] + "  " + words[4] + "  " + words[5] + "  " + words[6] + "  " + words[7];
		    			words[4] = "YES";
		    			updateVote = words[0] + "  " + words[1] + "  " + words[2] + "  " + words[3] + "  " + words[4] + "  " + words[5] + "  " + words[6] + "  " + words[7];
		    		}
		    		else if (office == "vp"){
		    			wordsJoin = words[0] + "  " + words[1] + "  " + words[2] + "  " + words[3] + "  " + words[4] + "  " + words[5] + "  " + words[6] + "  " + words[7];
		    			words[5] = "YES";
		    			updateVote = words[0] + "  " + words[1] + "  " + words[2] + "  " + words[3] + "  " + words[4] + "  " + words[5] + "  " + words[6] + "  " + words[7];
		    		}	
		    		else if (office == "s"){
		    			wordsJoin = words[0] + "  " + words[1] + "  " + words[2] + "  " + words[3] + "  " + words[4] + "  " + words[5] + "  " + words[6] + "  " + words[7];
		    			words[6] = "YES";
		    			updateVote = words[0] + "  " + words[1] + "  " + words[2] + "  " + words[3] + "  " + words[4] + "  " + words[5] + "  " + words[6] + "  " + words[7];
		    		}
		    		else if (office == "h"){
		    			wordsJoin = words[0] + "  " + words[1] + "  " + words[2] + "  " + words[3] + "  " + words[4] + "  " + words[5] + "  " + words[6] + "  " + words[7];
		    			words[7] = "YES";
		    			updateVote = words[0] + "  " + words[1] + "  " + words[2] + "  " + words[3] + "  " + words[4] + "  " + words[5] + "  " + words[6] + "  " + words[7];
		    		}
		    		else {
		    			System.out.println("Something went wrong.");		    		
		    		}
		    		String in = null;
		    		Scanner sc = new Scanner(file);
		    		StringBuffer sb = new StringBuffer();
		    		while (sc.hasNextLine()) {
		    			in = sc.nextLine() + "\n";
		    			sb.append(in);
		    		}
		    		String result = sb.toString();
		    		//System.out.println("Contents of the file before change: " + result);
		    		result = result.replaceAll(wordsJoin, updateVote);		      
		    		PrintWriter writer = new PrintWriter(file);
		    		writer.append(result);
		    		//System.out.println("Contents of the file after change: " + result);
		    		writer.flush();
		    		writer.close();
		    		System.exit(1);
		    	}
		    }
		}
	}
	
	public static void checkExistingID(String ID) throws Exception {
		
		File file = new File("VoterList.txt"); 
		String[] words = null;  
		FileReader fr = new FileReader(file);  
		BufferedReader br = new BufferedReader(fr); 
		Scanner input = new Scanner(System.in);
		String s;  
		while((s = br.readLine()) != null){
			words = s.split("  ");  
		    for (String word : words){
		    	if (word.equals(ID)){
		    		System.out.println("VoterID match found for: " + ID);
		    		if (words[4].equalsIgnoreCase("NO") || words[5].equalsIgnoreCase("NO") || words[6].equalsIgnoreCase("NO") || words[7].equalsIgnoreCase("NO")) {
		    			castVote(ID, words[4], words[5], words[6], words[7]);
		    		}
		    		else if (words[4].equalsIgnoreCase("YES") || words[5].equalsIgnoreCase("YES") || words[6].equalsIgnoreCase("YES") || words[7].equalsIgnoreCase("YES")) {
		    			System.out.println("All votes have been cast for this VoterID. Thank you for voting.");
		    			System.exit(1);
		    		}
		    		else {
		    			System.out.print("Something went wrong.");
		    			System.exit(1);
		    		}
		        }
		    	else {}
				}
		    }
		   
		System.out.println("No existing VoterID found for: " + ID + "\nWould you like to register now (y or n)?");
		if (input.nextLine().compareToIgnoreCase("y") == 0) {
			main(null);
		}else{System.exit(1);}
		fr.close();
	}
	
	public static void main(String args[]) {
		
		try {
			fileCreation();
			CandidateList.fileCreation();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Scanner input = new Scanner(System.in);
		System.out.println("Please select one: 'Admin' or 'User'");
		String choice = input.nextLine();
		if (choice.compareToIgnoreCase("admin") == 0) {
			System.out.println("Please enter password: ");
			choice = input.nextLine();
			if (choice.compareTo("Admin@1234*") == 0) {
				System.out.println("Please select one: 'Add Candidate' or 'Print Report'");
				choice = input.nextLine();
				if (choice.compareToIgnoreCase("add candidate") == 0) {
					try {
						CandidateList.checkExistingCandidate();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if (choice.compareToIgnoreCase("print report") == 0) {
					CandidateList.printReport();
				}
				else {System.out.println("Invalid selection. Try again."); main(null);}
			}else {System.out.println("Invalid credentials. Try again."); main(null);}
		}
		else if (choice.compareToIgnoreCase("user") == 0) {
			System.out.println("Please select one: 'Register' or 'Vote'");
			choice = input.nextLine();
			if (choice.compareToIgnoreCase("register") == 0) {
				System.out.println("Please enter full name followed by the enter key:");
				String name = input.nextLine();
				System.out.println("Please enter street address followed by the enter key:");
				String address1 = input.nextLine();
				System.out.println("Please enter city/state/zip (format 'City, ST #####') followed by the enter key:");
				String address2 = input.nextLine();
				try {
					checkExistingName(name, address1, address2);
					Integer ID = displayID(VoterID);
					System.out.println("Would you like to vote now? (y or n)");
					choice = input.nextLine();
					if (choice.compareToIgnoreCase("y") == 0) {
						checkExistingID(ID.toString());
					}
					else {System.out.println("Thank you for registering.");}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (choice.compareToIgnoreCase("vote") == 0) {
				System.out.println("Please enter VoterID: ");
				String ID = input.nextLine();
				try {
					checkExistingID(ID);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {System.out.println("Invalid selection. Please choose again.");main(null);}
		}else {System.out.println("Invalid selection. Try again."); main(null);}
	}
}
