package Voters;
import java.util.Scanner;
import java.io.*;
public class CandidateList {
	
	public static void checkExistingCandidate() throws Exception {
		
		File file = new File("CandidateList.txt"); 
		String[] words = null;  
		FileReader fr = new FileReader(file);  
		BufferedReader br = new BufferedReader(fr); 
		String s; 
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter candidate's full name followed by the enter key:");
		String name = input.nextLine();
		System.out.println("Please enter office running for (abbreviation only) followed by the enter key (P for President, VP for Vice President, S for Senate, H for House):");
		String office = input.nextLine();
		if (office.compareToIgnoreCase("p") == 0 || office.compareToIgnoreCase("vp") == 0 || office.compareToIgnoreCase("s") == 0 || office.compareToIgnoreCase("h") == 0) {
			
		}else {
			System.out.println("Invalid entry. Try again.");
			checkExistingCandidate();
		}
		
		String info = name + "  " + office.toUpperCase();   
		while((s = br.readLine()) != null){
			words = s.split("  ");  
		    for (String word : words){
		    	if (word.equals(name)){
		    		System.out.println("Matching entry found: " + word);
		    		System.out.println("\nUser input: ");
		    		System.out.println(info);
		    		System.out.println("\nData bank info: ");
		    		String wordsJoin = words[0] + "  " + words[1];
		    		System.out.println(wordsJoin);
		    		System.out.println("\nUpdate existing info? (y or n): ");
		    		Scanner inp = new Scanner(System.in);
		    		if (inp.next().compareToIgnoreCase("y") == 0) {
		    			System.out.println("Please re-enter password: ");
		    			if (inp.next().equals("Admin@1234*")) {
		    				System.out.println(info + " \t\treplaces\t " + wordsJoin);
		    				System.out.println("Is this correct (y or n)?");
		    				if (inp.next().compareToIgnoreCase("y") == 0) {
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
		    				    System.out.println("Continue (y or n)?");
		    				    if (inp.next().compareToIgnoreCase("y") == 0) {
		    				    	checkExistingCandidate();
		    				    }else{System.out.println("Session complete. Returning to start.");System.exit(1);}
		    				}	
		    				else {
		    					System.out.println("Session complete. Returning to start.");
				    			System.exit(1);
		    				}
		    			}
		    			else {
		    				System.out.println("Invalid entry. Try again (y or n)?");
		    				if (input.next().compareToIgnoreCase("y") == 0) {
		    					checkExistingCandidate();
		    				}else {System.out.println("End of execution.");System.exit(1);}
		    			}
		    		}else {System.out.println("Session complete. End of execution."); System.exit(1);}
		    	}
		    }
		} 
		System.out.println("No match found. Adding candidate.");
		addCandidate(info);
		fr.close();	
		System.out.println("Continue (y or n)?");
		Scanner in = new Scanner(System.in);
		String choice = in.nextLine();
		if (choice.compareToIgnoreCase("y") == 0) {
			checkExistingCandidate();
		}else {System.out.println("Session complete. End of execution."); System.exit(1);}
	}

	public static void addCandidate(String info)  throws IOException {
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter("CandidateList.txt", true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.println(info + "  TX  0");
			pw.flush();
		} finally {
			try {
				pw.close();
				bw.close();
				fw.close();
			} catch (IOException io) {}
		}
	}
	
    public static void candidateDisplay(String position) {
        try {
            File myFile = new File("CandidateList.txt");
            Scanner in = new Scanner(myFile);
            while (in.hasNextLine()) {
                String data = in.nextLine();
                String[] canArr = data.split("  ");
                if (position.equalsIgnoreCase("P") && canArr[1].equalsIgnoreCase("P")) {
                	data = canArr[0];
                	System.out.println(data);
            	}
            	else if (position == "VP" && canArr[1] == "VP") {
            		data = canArr[0];
                	System.out.println(data);
            	}
            }  
        }
        catch (Exception e) {
        	System.out.println("Error");
        	e.printStackTrace();
        }
    }
	
	public static void fileCreation() throws Exception {
        File file = new File("CandidateList.txt");
        if (file.exists()) {
        }
        else {
            System.out.println("File does not exist. Make file (y or n)?");
            Scanner answer = new Scanner(System.in);
            if (answer.next().compareToIgnoreCase("y") == 0) {
                PrintWriter create = new PrintWriter(file);
                create.println("Name:" + "  " + "Position:" + "  " + "State:" + "  " + "Votes:");
                create.close();
            }
            else {
                System.out.print("Operation aborted.");
                System.exit(1);
            }
        }
    }
	
    public static void castVote(String office) {
      
        Scanner in = new Scanner(System.in);
        String voteChoice = "";
        if (office.equalsIgnoreCase("p")) {
            System.out.println("Please enter the full name of the Candidate to vote for. Here are the candidates : \n");
            try {
                File myFile = new File("CandidateList.txt");
                Scanner inp = new Scanner(myFile);
                String[] canArr = {};
                while (inp.hasNextLine()) {
                    String data = inp.nextLine();
                    canArr = data.split("  ");
                    if (canArr[1].equalsIgnoreCase("p")){
                        System.out.println(canArr[0]);
                    }
                }
                voteChoice = in.nextLine();
                System.out.println("You have submitted a vote for " + voteChoice + ". Thank you for voting.");
                inp.close();
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        }
        else if (office.equalsIgnoreCase("vp")) {
            System.out.println("Please enter the full name of the Candidate to vote for. Here are the candidates : \n");
            try {
                File myFile = new File("CandidateList.txt");
                Scanner inp = new Scanner(myFile);
                while (inp.hasNextLine()) {
                    String data = inp.nextLine();
                    String[] canArr = data.split("  ");
                    if (canArr[1].equalsIgnoreCase("vp")){
                        System.out.println(canArr[0]);
                    }
                }
                
                voteChoice = in.nextLine();
                System.out.println("You have submitted a vote for " + voteChoice + ". Thank you for voting.");
                inp.close();
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        }
        else if (office.equalsIgnoreCase("s")) {
            System.out.println("Please enter the full name of the Candidate to vote for. Here are the candidates : \n");
            try {
                File myFile = new File("CandidateList.txt");
                Scanner inp = new Scanner(myFile);
                while (inp.hasNextLine()) {
                    String data = inp.nextLine();
                    String[] canArr = data.split("  ");
                    if (canArr[1].equalsIgnoreCase("s")){
                        System.out.println(canArr[0]);
                    }
                }
                voteChoice = in.nextLine();
                System.out.println("You have submitted a vote for " + voteChoice + ". Thank you for voting.");
                inp.close();
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        }
        else if (office.equalsIgnoreCase("h")) {
            System.out.println("Please enter the full name of the Candidate to vote for. Here are the candidates : \n");
            try {
                File myFile = new File("CandidateList.txt");
                Scanner inp = new Scanner(myFile);
                while (inp.hasNextLine()) {
                    String data = inp.nextLine();
                    String[] canArr = data.split("  ");
                    if (canArr[1].equalsIgnoreCase("h")){
                        System.out.println(canArr[0]);
                    }
                }                
                voteChoice = in.nextLine();
                System.out.println("You have submitted a vote for " + voteChoice + ". Thank you for voting.");
                inp.close();
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        }
        in.close();

        try {
            File myFile = new File("CandidateList.txt");
            Scanner input = new Scanner(myFile);
            String [] arr = new String[4];
            while (input.hasNextLine()) {
                String data = input.nextLine();
                String[] canArr = data.split("  ");
                if (canArr[0].equalsIgnoreCase(voteChoice)) {
                	arr = canArr;
                    canArr[3] = Integer.toString(Integer.parseInt(canArr[3]) + 1);
                    //System.out.println(voteChoice + " now has " + canArr[3] + " votes.");
                }
            }
            input.close();
            File file = new File("CandidateList.txt"); 
    		String[] words = null;  
    		FileReader fr = new FileReader(file);  
    		BufferedReader br = new BufferedReader(fr); 
    		String s;  
    		String info = arr[0] + "  " + arr[1] + "  " + arr[2] + "  " + arr[3];   
    		while((s = br.readLine()) != null){
    			words = s.split("  ");  
    		    for (String word : words){
    		    	if (word.equals(voteChoice)){
    		    		String wordsJoin = words[0] + "  " + words[1] + "  " + words[2] + "  " + words[3];
    		    		String inp = null;
    		    		Scanner sc = new Scanner(file);
    		    		StringBuffer sb = new StringBuffer();
    		    		while (sc.hasNextLine()) {
    		    			inp = sc.nextLine() + "\n";
    		    			sb.append(inp);
    		    		}
    		    		String result = sb.toString();
    		    		//System.out.println("Contents of the file before change: "+result);
    		    		result = result.replaceAll(wordsJoin, info);  
    		    		PrintWriter writer = new PrintWriter(file);
    		    		writer.append(result);
    		    		//System.out.println("Contents of the file after change: "+result);
    		    		writer.flush();
    		    		writer.close();
    		    	}
    		    }
    		}
        }
         catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    
    public static void printReport() {
    	System.out.println("Please enter desired report: VoterList or CandidateList");
    	Scanner input = new Scanner(System.in);
    	String choice = input.nextLine();
    	if (choice.compareToIgnoreCase("voterlist") == 0) {
    		File file = new File("VoterList.txt");
    		if (file.exists()) {
    			System.out.println("This file can be found here: " + file.getAbsoluteFile() + "\nPrint data to console (y or n)?");
    			choice = input.next();
    			if (choice.compareToIgnoreCase("y") == 0) {
    				try {
						Scanner reader = new Scanner(file);
						while(reader.hasNext()) {
							String line = reader.nextLine();
							System.out.println(line);
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
    			}else {System.out.println("System exiting.");System.exit(1);}
    		}
    		else {
    			System.out.println("This file does not exist. Do you want to create the file (y or n)?");
    			if (choice.compareToIgnoreCase("y") == 0) {
    				try {
						VoterList.fileCreation();
					} catch (Exception e) {
						e.printStackTrace();
					}
    			}else {System.out.println("System exiting.");System.exit(1);}
    		}
    	}else if (choice.compareToIgnoreCase("candidatelist") == 0){
    		File file = new File("CandidateList.txt");
    		if (file.exists()) {
    			System.out.println("This file can be found here: " + file.getAbsoluteFile() + "\nPrint data to console (y or n)?");
    			choice = input.next();
    			if (choice.compareToIgnoreCase("y") == 0) {
    				try {
						Scanner reader = new Scanner(file);
						while(reader.hasNext()) {
							String line = reader.nextLine();
							System.out.println(line);
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
    			}else {System.out.println("System exiting.");System.exit(1);}
    		}
    		else {
    			System.out.println("This file does not exist. Do you want to create the file (y or n)?");
    			if (choice.compareToIgnoreCase("y") == 0) {
    				try {
						fileCreation();
					} catch (Exception e) {
						e.printStackTrace();
					}
    			}else {System.out.println("System exiting.");System.exit(1);}
    		}
    	}else {System.out.println("Invalid entry. Try again.");printReport();}
    }
    		
}
