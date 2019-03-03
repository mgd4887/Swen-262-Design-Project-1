package request;

import java.util.List;
import Response.BookSearch;
import books.Book;


public class LibraryBookSearch extends BookSearch {
    public LibraryBookSearch(String _title, List<Object> params){
        return;
    }


    public List<Object> ExecuteCommand(){
        return null;
    }

    public List<Book> bookList(List<Object> params){
        return null;
    }
//    public void setUserSelection(List<Long> books){
//        return null;
//    }
}