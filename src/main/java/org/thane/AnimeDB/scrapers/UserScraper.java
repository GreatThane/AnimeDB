package org.thane.AnimeDB.scrapers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thane.AnimeDB.entities.Anime;
import org.thane.AnimeDB.entities.MalUser;
import org.thane.AnimeDB.entities.WatchListEntry;
import org.thane.AnimeDB.exceptions.AccountException;
import org.thane.AnimeDB.exceptions.AccountNotFoundException;
import org.thane.AnimeDB.exceptions.PrivateAccountException;
import org.thane.AnimeDB.repositories.AnimeRepository;
import org.thane.AnimeDB.repositories.UserRepository;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserScraper {

    private UserRepository userRepository;
    private AnimeRepository animeRepository;

    @Autowired
    private UserScraper(UserRepository userRepository, AnimeRepository animeRepository) {
        this.userRepository = userRepository;
        this.animeRepository = animeRepository;
    }

    private static final Pattern profilePattern = Pattern.compile("<div\\s+style=\"margin-bottom:\\s+\\d+px;\"><a\\s+href=\"/profile/.*\">(.*)</a></div>");

    public void userPageScrape() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(5000, 7000));
            URLConnection connection = new URL("https://myanimelist.net/users.php").openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();

            Matcher profileMatcher = profilePattern.matcher(content);
            while (profileMatcher.find()) {
                String username = profileMatcher.group(1);
                try {
                    Set<WatchListEntry> watchList = usernameToWatchList(username);
                    if (userRepository.findByUsername(username).size() == 0) {
                        MalUser user = new MalUser(username);
                        userRepository.save(user);
                    }
                    for (WatchListEntry watchListEntry: watchList) {
                        Anime anime = new Anime(watchListEntry);
                        animeRepository.save(anime);
                    }

                } catch (AccountException ex) {
                    userRepository.deleteByUsername(username);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final Pattern listFinderPattern = Pattern.compile("<table\\s+class=\"list-table\"\\s+data-items=\"(.*?)\">", Pattern.DOTALL | Pattern.MULTILINE);

    public static Set<WatchListEntry> usernameToWatchList(String username) throws AccountException {
        try {
            URLConnection connection = new URL("http://myanimelist.net/animelist/" + username + "?status=2&order=4&order2=0").openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();
            content = content.replaceAll("&quot;", "\"");
            if (content.contains("Moved Permanently")) {
                String redirect = connection.getHeaderField("Location");
                if (redirect != null) {
                    connection = new URL(redirect).openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                    scanner = new Scanner(connection.getInputStream());
                    scanner.useDelimiter("\\Z");
                    content = scanner.next();
                    scanner.close();
                    content = content.replaceAll("&quot;", "\"");
                }
            }
            Matcher listFinderMatcher = listFinderPattern.matcher(content);
            if (!listFinderMatcher.find()) return new HashSet<>();
            JsonArray array = new Gson().fromJson(listFinderMatcher.group(1), JsonArray.class);

            Set<WatchListEntry> watchListEntrySet = new HashSet<>();
            Iterator<JsonElement> iterator = array.iterator();
            while (iterator.hasNext()) {
                try {
                    WatchListEntry watchListEntry = new Gson().fromJson(iterator.next(), WatchListEntry.class);
                    watchListEntrySet.add(watchListEntry);
                } catch (JsonSyntaxException e) {
                }
            }
            return watchListEntrySet;
        } catch (IOException e) {
            try {
                if (e.getMessage().contains("403")) {
                    System.out.println("403'd on " + username);
                    throw new PrivateAccountException(username);
                } else if (e.getMessage().contains("429")) {
                    System.out.println("429'd on " + username);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(9000, 10000));
                    return usernameToWatchList(username);
                } else if (e.getMessage().contains("404")) {
                    throw new AccountNotFoundException(username);
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        return new HashSet<>();
    }
}
