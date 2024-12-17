import java.util.Scanner;
import java.util.Vector;
import java.util.InputMismatchException;

class X
{
    public static boolean first_vote = false;
    public static boolean first_voter = false;
}

class Voter
{
    String voterId;
    String voterName;
    boolean voted = false;
    String pass;
    
    public Voter(String voterId, String name, String pass) 
	{
        this.voterId = voterId;
        this.voterName = name;
        this.voted = false;
        this.pass = pass;
    }

	public String toString() 
	{ 
	   return "\n Voter : " + "voterId=" + voterId + ", voterName='" + voterName  + "', voted=" + voted + ", pass='" + pass + "'"; 
       // return "Hello from toString()";
	}
}

class Candidate 
{
    int num;
    String name;
    int votesCount;
    public Candidate(int num, String name)
	{
        this.num = num;
        this.name = name;
        this.votesCount = 0;
    }

    public String toString() 
	{ 
	   return "Candidate :- " + " choice : " + num + ", Name : " + name + ", VotesCount : " + votesCount; 
	} // end of toString() method 

     static  Vector<Candidate> c1 = new Vector<Candidate>(10);
     static
    {
        c1.addElement(new Candidate(1, "Nana Patekar"));
        c1.addElement(new Candidate(2, "Ashok Saraf"));
        c1.addElement(new Candidate(3, "Laxmikant berde"));
        c1.addElement(new Candidate(4, "Sachin Pilgaonkar"));
    }

}// end of Candicate class

public class VotingProcess 
{
	
      static Vector<Voter> v1 = new Vector<>();
      public static void displayCandidateList() 
      {
        System.out.println("Registered Candidates:");
        for (int i = 0; i <Candidate.c1.size(); i++) 
        {
            System.out.println(Candidate.c1.get(i).toString());
        }
      } // end of displayCandidateList() method
    
    public static void voterRegistration()
	{
        Scanner sc = new Scanner(System.in);
        
        try
		{
            System.out.println("Enter your age ");
            int age = sc.nextInt();
            sc.nextLine(); 
            if (age >= 18) 
			{
                System.out.println("Enter your Name ");
                String name = sc.nextLine();
                
                System.out.println("Enter your VoterId ");
                String voterId = sc.nextLine();
                
                System.out.println("Create a Strong password");
                String pass = sc.nextLine();
                
                v1.add(new Voter(voterId, name, pass));
                System.out.println("Voter registered successfully!!!");
                X.first_voter = true;
                System.out.println(v1.toString());
            } 
			else 
			{
                System.out.println("... You are not eligible for voting ... ");
            }
        } 
		catch (InputMismatchException ime) 
		{
            System.out.println("Invalid input. Please enter valid age.");
            sc.next();
        }
    } // end of voterRegistration() method

    public static boolean voterLogin(String voterIdforlogin)
	{
        boolean voterfound = false;
        Scanner sc = new Scanner(System.in);
        try 
		{
            for (Voter i : v1) 
			{
                if (voterIdforlogin.equals(i.voterId)) 
				{
                    voterfound = true;
                    System.out.println("Enter the password");
                    String enteredPass = sc.nextLine();
                    if(enteredPass.equals(i.pass)) 
					{
                        System.out.println("Login Successful..\n");
                        return true;
                    }
					else 
					{
                        System.out.println("Password Did not Match..\n");
                        return false;
                    }
                }
            }
            if(voterfound == false)
            {
                System.out.println("Invalid Voter ID...\n");
                return false;
            }
        } 
		catch (InputMismatchException ime) 
		{
            System.out.println("Invalid input. Inalid Voter ID...\n");
            sc.next();
        }
        return false;
    } //end of voterLogin() method

    public static void vote() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your VoterId ");
        String voterId1 = sc.nextLine();
        if(X.first_voter == true)
        {
            boolean login_result = voterLogin(voterId1);
            if(login_result == false)
            {
                vote();
            }
            else if(login_result == true)
            {
                boolean voter_found = false;
                    try 
                    {
                        for (Voter i : v1) 
                        {
                            if (voterId1.equals(i.voterId) && i.voted==false)
                            {
                                displayCandidateList() ;               
                                System.out.print("Enter candidate number: ");
                                int no = sc.nextInt();
                                sc.nextLine(); 
                
                                if (no >= 1 && no <= Candidate.c1.size()) 
                                {
                                    Candidate selectedCandidate = Candidate.c1.get(no - 1);
                                    selectedCandidate.votesCount++;
                                    i.voted = true;
                                    System.out.println("Voted successfully!");
                                    X.first_vote = true;
                                    voter_found = true;
                                    break;
                                }
                                else 
                                {
                                    System.out.println("Invalid choice.");
                                }
                            }                
                        } // end of for-each loop

                        if(voter_found == false)
                        {
                            System.out.println("Already Voted..\n");
                        }
                    }
                    catch (InputMismatchException ime) 
                    {
                        System.out.println("Invalid input. Please enter valid number.");
                        sc.next(); 
                    }
            } // end of else if..
        }
        else if(X.first_voter == false)
        {
            System.out.println("... No Voter is registered YET.. \n");
        } 
    } // end of vote() method
    
    public static void displayResult() 
    {
        if(X.first_vote == false)
        {
            System.out.println("Voting Process yet not Instantiated..");
            System.out.println("No Winners YET..");
        }
        else
        {
            System.out.println("Result:");
            Candidate winner = null;
            for (int i = 0; i < Candidate.c1.size(); i++) 
            {
                System.out.println(Candidate.c1.get(i).toString());
                
                if (winner == null || Candidate.c1.get(i).votesCount > winner.votesCount) 
                {
                    winner = Candidate.c1.get(i);
                }
            }
            
            System.out.println("Winner: " + winner.name + " with " + winner.votesCount + " votes");
        }
    } //end of display_result() method

	public static void main(String ar[])
	{
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("\n\n....Voting System....\n Welcome to the system\n");
            System.out.println("1. Register Voter");
            System.out.println("2. Make Vote");
            System.out.println("3. View Result");
            System.out.println("4. To Exit");
            System.out.print("Provide your choice : ");
            int ch=sc.nextInt();
            switch(ch) 
            {
                case 1: 
                VotingProcess.voterRegistration();
                // System.out.println(v1.toString());
                break;
                
                case 2:
                VotingProcess.vote();
                break;

                case 3:
                VotingProcess.displayResult();
                break;
             
                case 4:
                System.out.println("Exiting");
                return;

                default:
                System.out.println("Enter the valid input");
                continue;
            }
        // sc.close();
        }
        
	}
}