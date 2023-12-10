import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Scanner;
 class Member {
    private String name;
    private String id;
   public Member(String name, String id) {
        this.name = name;
        this.id = id;
    }
   public String getName() {
        return name;
    }
   public void setName(String name) {
        this.name = name;
    }
public String getId() {
        return id;
    }
  public void setId(String id) {
        this.id = id;
    }
}
class People {
    private List<Member> members;
   public People() {
        this.members = new ArrayList<>();
    }
   public void addMember(String name, String id) {
        Member newMember = new Member(name, id);
        members.add(newMember);
        System.out.println("Member Name: " + name + "\nMember CNIC: " + id);
        System.out.println("New Member Registered");
    }
  public void displayMembers() {
        System.out.println("Registered Member Details:");
        for (Member member : members) {
            System.out.println("Member Name: " + member.getName() + " by Member CNIC: " + member.getId());
        }
    }
}
class Book {
    private String title;
    private String author;
    private String genre;
    private int quantity;

    public Book(String title, String author, String genre, int quantity) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class Library {
    private ArrayList<Book> books;
    private ArrayList<Book> borrowedBooks;
    private Map<String, Book> borrowedBooksWithID;
    private int borrowIDCounter;

    public Library() {
        books = new ArrayList<>();
        borrowedBooks = new ArrayList<>();
        borrowedBooksWithID = new HashMap<>();
        borrowIDCounter = 1;
        initializeAvailableBooks(); 
    }

    private void initializeAvailableBooks() {
        // Adding predefined books to the library
        books.add(new Book("Effective Java", "Josh", "Programming", 5));
        books.add(new Book("Php", "4 Pilars", "Programming", 3));
        books.add(new Book("Exception Handling", "Ks", "Programming", 7));
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added to the library.");
    }

    public void displayAvailableBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            System.out.println("Available Books:");
            for (Book book : books) {
                System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Genre: " + book.getGenre() + ", Quantity: " + book.getQuantity());
            }
        }
        System.out.println("Number of available books: " + books.size());
        System.out.println("Number of borrowed books: " + borrowedBooks.size());
    }

    public void borrowBook(String title) {
        Book foundBook = searchByTitle(title);
        if (foundBook != null && !borrowedBooks.contains(foundBook) && foundBook.getQuantity() > 0) {
            borrowedBooks.add(foundBook);
            foundBook.setQuantity(foundBook.getQuantity() - 1);
            String borrowID = generateBorrowID();
            borrowedBooksWithID.put(borrowID, foundBook);
            System.out.println("Book '" + title + "' has been borrowed with ID: " + borrowID);
        } else if (foundBook != null && borrowedBooks.contains(foundBook)) {
            System.out.println("Sorry, the book '" + title + "' is already borrowed.");
        } else if (foundBook != null && foundBook.getQuantity() <= 0) {
            System.out.println("Sorry, the book '" + title + "' is out of stock.");
        } else {
            System.out.println("Book '" + title + "' not found in the library.");
        }
    }

 public void returnBook(String title) {
    Book foundBook = searchByTitle(title);
    if (foundBook != null) {
        System.out.print("Enter the ID of book to return: ");
        Scanner scanner = new Scanner(System.in);
        String returnID = scanner.nextLine();
        if (borrowedBooksWithID.containsKey(returnID) && borrowedBooksWithID.get(returnID).getTitle().equalsIgnoreCase(title)) {
            borrowedBooks.remove(foundBook);
            foundBook.setQuantity(foundBook.getQuantity() + 1);
            borrowedBooksWithID.remove(returnID);
            System.out.println("Book with ID '" + returnID + "' has been returned.");
        } else {
            System.out.println("Book with ID '" + returnID + "' not found or does not match the title.");
        }
    } else {
        System.out.println("Book '" + title + "' not found in the library.");
    }
}


    public Book searchByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    private String generateBorrowID() {
        return "B" + borrowIDCounter++;
    }
}
class Main{

  public void menu() {
	  System.out.println("Select an option");
        System.out.println("1) to add-book");
        System.out.println("2) to Register Members");
        System.out.println("3) to Delete Members");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
               Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        System.out.print("Enter the number of books to add: ");
        int numOfBooksToAdd = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 0; i < numOfBooksToAdd; i++) {
            System.out.println("\nEnter details of book " + (i + 1) + ":");
            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Author: ");
            String author = scanner.nextLine();
            System.out.print("Genre: ");
            String genre = scanner.nextLine();
            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            Book newBook = new Book(title, author, genre, quantity);
            library.addBook(newBook);
        }

        library.displayAvailableBooks();

        System.out.print("\nEnter the number of books to borrow: ");
        int numOfBooksToBorrow = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 0; i < numOfBooksToBorrow; i++) {
            System.out.print("Enter the title of book " + (i + 1) + " to borrow: ");
            String borrowTitle = scanner.nextLine();
            library.borrowBook(borrowTitle);
        }

        library.displayAvailableBooks();

        System.out.print("\nEnter the number of books to return: ");
        int numOfBooksToReturn = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 0; i < numOfBooksToReturn; i++) {
            System.out.print("Enter the title of book " + (i + 1) + " to return: ");
            String returnTitle = scanner.nextLine();
            library.returnBook(returnTitle);
        }

        library.displayAvailableBooks();

        scanner.close();
                break;
				case 2:
    System.out.println("You chose option 2");
    Scanner scanne = new Scanner(System.in);
 System.out.println("Enter Member name: ");
    String name = scanne.nextLine();
 System.out.println("Enter Member CNIC: ");
    String id = scanne.nextLine();
	People ns = new People();
   ns.addMember(name, id);
    break; 
	case 3:
	System.out.println("Delete member");
	  DelManager delManager = new DelManager();
        delManager.addDel("John Doe", "1234");
        delManager.addDel("Alice Smith", "5678");
        delManager.addDel("Bob Johnson", "9012");

        Scanner scan = new Scanner(System.in);

        System.out.println("Members in the system:");
        delManager.displayDels();

        System.out.print("Enter the name of the member to delete: ");
        String nameToDelete = scan.nextLine();

        delManager.deleteDelByName(nameToDelete);

        System.out.println("Updated Members list:");
        delManager.displayDels();
	 default:
                System.out.println("Invalid option selected.");
                break;
				
				}   }
}

class User  {
    List<String> borrowedBooks = new ArrayList<>();
 
    public void menu() {
    
        Scanner s = new Scanner(System.in);
        int dec = s.nextInt();
        switch (dec) {
            case 1:
                System.out.println("Available Books are:");
                String[] bookTitles1 = {"Effective Java", "Head First Java", "Java: The Complete Reference", "Clean Code"};
                for (String title : bookTitles1) {
                    System.out.println(title);
                }
                
                  System.out.println("\nBorrowed Books are:");
        String[] bookTitles = {"Effective Java", "Head First Java"};
        String[] bookMember = {"Joshua", "Kathy Sierra "};
 String[] bookId = {"B1", "B2"};
        for (int i = 0; i < bookTitles.length; i++) {
            System.out.println("Title: " + bookTitles[i] + " - Author: " + bookMember[i] + " - Member ID"+ bookId[i]);
        }
		System.out.println("\nMember Name & CNIC are:");
        String[] bookTitles7 = {"Emily Johnson", "Alexander Rodriguez", "Mia Chang", "Daniel Patel"};
        String[] bookAuthors = {"1233...", "1233... ", "1233...", "1233..."};
        for (int i = 0; i < bookTitles.length; i++) {
            System.out.println("Member Name: " + bookTitles[i] + " - CNIC: " + bookAuthors[i]);
        }
				
                break;
            case 2:System.out.println("Available Books are:");
String[] bookTitles2 = {"Effective Java", "Head First Java", "Java: The Complete Reference", "Clean Code"};
for (String title : bookTitles2) {
    System.out.println(title);
}

Scanner borrowScanner = new Scanner(System.in);
System.out.println("Enter the number of books to borrow:");
int numberOfBooksToBorrow = borrowScanner.nextInt();
borrowScanner.nextLine(); 

List<String> borrowedBooks = new ArrayList<>(); 

for (int j = 0; j < numberOfBooksToBorrow; j++) {
    System.out.println("Enter the name of book " + (j + 1) + " to borrow:");
    String bookToBorrow = borrowScanner.nextLine();
    boolean bookFound = false;
    for (int i = 0; i < bookTitles2.length; i++) {
        if (bookTitles2[i] != null && bookTitles2[i].equalsIgnoreCase(bookToBorrow)) {
            bookTitles2[i] = null; 
            borrowedBooks.add(bookToBorrow); 
            bookFound = true;
            break;
        }
    }
    if (!bookFound) {
        System.out.println("Book '" + bookToBorrow + "' not found in the available books.");
    }
}
System.out.println("Books borrowed:");
for (String book : borrowedBooks) {
    System.out.println(book);
}
System.out.println("Available Books after borrowing:");
for (String title : bookTitles2) {
    if (title != null) {
        System.out.println(title);
    }
} 
System.out.println("Return Books:");
if (borrowedBooks.isEmpty()) {
    System.out.println("No books borrowed to return.");
} else {
    System.out.println("Books Borrowed:");
    for (String book : borrowedBooks) {
        System.out.println(book);
    }
    Scanner returnScanner = new Scanner(System.in);
    System.out.println("Enter the number of books to return:");
    int numberOfBooksToReturn = returnScanner.nextInt();
    returnScanner.nextLine();
    for (int k = 0; k < numberOfBooksToReturn; k++) {
        System.out.println("Enter the name of book " + (k + 1) + " to return:");
        String bookToReturn = returnScanner.nextLine();
        boolean bookReturned = false;
        for (int i = borrowedBooks.size() - 1; i >= 0; i--) {
            if (borrowedBooks.get(i).equalsIgnoreCase(bookToReturn)) {
                borrowedBooks.remove(i);
                bookReturned = true;
                break;
            }
        }
        if (!bookReturned) {
            System.out.println("Book '" + bookToReturn + "' was not found.");
        }
    }
    System.out.println("Updated Borrowed Books:");
    for (String book : borrowedBooks) {
        System.out.println(book);
    }
}
break;
case 3:  BookSearch library = new BookSearch();
        Scanner r = new Scanner(System.in);
        System.out.print("Enter the book title or author to search for: ");
        String searchTerm = r.nextLine();
        List<String> foundBooks = library.searchBook(searchTerm);
        if (!foundBooks.isEmpty()) {
            System.out.println("Books found for \"" + searchTerm + "\":");
            for (String bookTitle : foundBooks) {
                System.out.println("Book Titile is: " + bookTitle);
            }
        } else {
            System.out.println("No books found for \"" + searchTerm + "\".");
        }

    }
}
}
class BookSearch {
   private String[][] books;
    public BookSearch() {
        books = new String[][]{
            {"Effective Java", "Joshua Bloch"},
            {"Head First Java", "Kathy Sierra"},
            {"Java: The Complete Reference", "Herbert Schildt"},
            {"Clean Code", "Robert C. Martin"}
        };
    }
    public List<String> searchBook(String searchTerm) {
        List<String> matchingBooks = new ArrayList<>();
        for (String[] book : books) {
            if (book[0].equalsIgnoreCase(searchTerm)) {
                matchingBooks.add(book[0]);
            } else if (book[1].equalsIgnoreCase(searchTerm)) {
                matchingBooks.add(book[0]);
            }
        }
        return matchingBooks;}
}
class Del {
    private String name;
    private String id;
    public Del(String name, String id) {
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    @Override
    public String toString() {
        return "Member Name: " + name + ", Member ID: " + id;
    }
}
class DelManager {
    private List<Del> dels;
    public DelManager() {
        this.dels = new ArrayList<>();
    }
    public void addDel(String name, String id) {
        Del newDel = new Del(name, id);
        dels.add(newDel);
        System.out.println("New Member Registered: " + newDel);
    }
    public void displayDels() {
        System.out.println("Registered Member Details:");
        for (Del del : dels) {
            System.out.println(del);
        }
    }
    public void deleteDelByName(String nameToDelete) {
        Iterator<Del> iterator = dels.iterator();
        while (iterator.hasNext()) {
            Del del = iterator.next();
            if (del.getName().equalsIgnoreCase(nameToDelete)) {
                iterator.remove();
                System.out.println("Member '" + nameToDelete + "' has been deleted.");
                return;
            }
        }
        System.out.println("Member '" + nameToDelete + "' not found.");
    }
}
public class Project {
    public static void main(String[] args) {
		  System.out.println("\tLibrary Management Project");
		          System.out.println("Select an option");
		          System.out.println("1) Admin Panel  2) User Panel");
				  Scanner sc= new Scanner(System.in);
				  int choose=sc.nextInt();
				  switch(choose){
					  case 1:
					  System.out.println("Welcome to Admin Panel");
        Main result = new Main();
        result.menu();
		break;
		case 2:
		  System.out.println("Welcome to User Panel");
		  System.out.println("1) Diaplay Information");
		  System.out.println("2) Borrow/Return Books:");
		  System.out.println("3) Search Functionality:");
		  User na = new User();
		  na.menu();
    }}
}
