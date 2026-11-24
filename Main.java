import java.util.Scanner;

class Book {
    String books;
    private int Id;
    private String Title; 
    private Boolean isBorrowed;
    private String Author;
    
    public Books(){
        books = "All";
        Id = 0000;
        Title = "Javolenese";
        Author = "Author Nery";
        isBorrowed=false;
    }
    public Books(String B, int I, String T, String A){
        this.books = B;
        this.Id = I;
        this.Title = T;
        this.Author = A;
        this.isBorrowed = false;
    }
    public Int getId()
    {return Id;}
    public String getTitle()
    {return Title;}
    public void setBorrowed(boolean B){
        isBorrowed = B;}
    public void borrowBook(){
        if (isBorrowed) {isBorrowed = true;
            System.out.println(Title + " is borrowed.");
        } else System.out.println(Title + " is already borrowed."); }
    public void returnedBook() {
        if (isBorrowed) {isBorrowed = false;
            System.out.println(Title + "is Returned.");
        } else System.out.println(Title + "is not Borrowed.");
    }
    public void getDetails() {
        System.out.println("ID: " + Id + " Author: " + Author + " Title: " + Title + "Category: " + books);
    }

}
    public class Main {
    public static void main(String[] args) {
        while (true) {
        Scanner sc = new Scanner(System.in);
        Book bks1 = new Book("Javolenese", 0001, "Author Nery", "Nery");
        Book bks2 = new Book("Javonese", 0002, "James Author", "James");
        Book bks3 = new Book("Javwoniese", 0003, "Mark Author", "Mark");
        
        
            System.out.println("1.Borrow ");
            System.out.println("2.Return ");
            System.out.println("3.Catalog ");
            System.out.println("4.Quit");

            int choice = sc.nextInt();

        if (choice == 1) {
            System.out.println("ID: ");
            int Id = sc.nextInt();
        } else if (Id == (bks1.getDetails())) bks1.borrowBook();
        else if (Id == (bks2.getDetails())) bks2.borrowBook();
        else if (Id == (bks3.getDetails())) bks3.borrowBook();
        else {
            System.out.println("Invalid id.");
        }
        if (choice == 2) {
            int Id = sc.nextInt();
        } else if (Id == (bks1.getDetails())) bks1.returnedBook();
        else if (Id == (bks2.getDetails())) bks2.returnedBook();
        else if (Id == (bks3.getDetails())) bks3.returnedBook();
        
        if (choice == 3) {
            bks1.getDetails();
            bks2.getDetails();
            bks3.getDetails();
        }

        if (choice == 4) {
            System.out.println("Bye bye!");
        }
        sc.close();
        
            }
        }
    }
