package group5.swp391.onlinelearning.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PagingUtils {

    public <T> List<T> getPagingList(String pageChooes,
            List<T> listAll, int numberPerPage) {
        int page;

        if (pageChooes == null) {
            page = 1;
        } else {
            page = Integer.parseInt(pageChooes);
        }

        int begin;
        int end;
        begin = (page - 1) * numberPerPage;

        end = Math.min(listAll.size(), page * numberPerPage);

        List<T> listPerPage = new ArrayList<>();
        listPerPage = getListByPage(begin, end, listAll);

        return listPerPage;
    }

    private <T> List<T> getListByPage(int begin, int end,
            List<T> listAll) {
        List<T> listPerPage = new ArrayList<>();

        listPerPage.addAll(listAll.subList(begin, end));
        return listPerPage;
    }

    public <T> int getNumberOfPage(List<T> listAll, int numberPerPage) {
        return ((listAll.size() % numberPerPage == 0)
                ? (listAll.size() / numberPerPage)
                : (listAll.size() / numberPerPage + 1));
    }

    public List<Integer> getListPageNumber(int numberOfPage) {
        List<Integer> listPageNumber = new ArrayList<>();
        for (int i = 1; i <= numberOfPage; i++) {
            listPageNumber.add(i);
        }
        return listPageNumber;
    }
}
