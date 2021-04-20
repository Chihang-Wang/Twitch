package com.chihang.jupiter.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.chihang.jupiter.external.TwitchClient;
import com.chihang.jupiter.external.TwitchException;
import com.fasterxml.jackson.databind.ObjectMapper;



@WebServlet(name = "GameServlet", urlPatterns = {"/game"})
public class GameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gamename = request.getParameter("gamename");
        response.getWriter().print("Game is: " + gamename);


        /*response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        Game game = new Game("World of Warcraft", "Blizzard Entertainment", "Feb 11, 2005", "https://www.worldofwarcraft.com", 49.99);
        response.getWriter().print(mapper.writeValueAsString(game));*/

        // Get gameName from request URL.
        String gameName = request.getParameter("game_name");
        TwitchClient client = new TwitchClient();

        // Let the client know the returned data is in JSON format.
        response.setContentType("application/json;charset=UTF-8");
        try {
            // Return the dedicated game information if gameName is provided in the request URL, otherwise return the top x games.
            if (gameName != null) {
                response.getWriter().print(new ObjectMapper().writeValueAsString(client.searchGame(gameName)));
            } else {
                response.getWriter().print(new ObjectMapper().writeValueAsString(client.topGames(0)));
            }
        } catch (TwitchException e) {
            throw new ServletException(e);
        }

    }
}
