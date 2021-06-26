package myproject.nyt;

import org.junit.Test;

import myproject.nyt.ui.PopularArticle;
import myproject.nyt.ui.SearchArticle;

import static org.junit.Assert.assertFalse;

public class SearchQueryTest
{
    @Test
    public void searchArticle_NullQuery_ReturnsFalse() {
        assertFalse(SearchArticle.str_query.isEmpty());
    }
    @Test
    public void popularArticle_NullQuery_ReturnsFalse() {
        assertFalse(PopularArticle.str_query.isEmpty());
    }
}
