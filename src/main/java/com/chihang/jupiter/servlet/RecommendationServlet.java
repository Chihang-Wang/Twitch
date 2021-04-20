package com.chihang.jupiter.servlet;

import com.chihang.jupiter.Recommendation.ItemRecommender;
import com.chihang.jupiter.Recommendation.RecommendationException;
import com.chihang.jupiter.entity.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "RecommendationServlet", urlPatterns = {"/recommendation"})
public class RecommendationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        ItemRecommender itemRecommender = new ItemRecommender();
        Map<String, List<Item>> itemMap;

        // If the user is successfully logged in, recommend by the favorite records, otherwise recommend by the top games.
        try {
            if (session == null) {
                itemMap = itemRecommender.recommendItemsByDefault();
            } else {
                String userId = (String) request.getSession().getAttribute("user_id");
                itemMap = itemRecommender.recommendItemsByUser(userId);
            }
        } catch (RecommendationException e) {
            throw new ServletException(e);
        }

        com.chihang.jupiter.servlet.ServletUtil.writeItemMap(response, itemMap);
    }

}

