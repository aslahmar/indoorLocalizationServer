package com.ucergy.ilocator.controller;

import com.datastax.driver.core.utils.UUIDs;
import com.ucergy.ilocator.model.UserMoves;
import com.ucergy.ilocator.model.UserMovesReceived;
import com.ucergy.ilocator.repository.UserMovesRepository;
import com.ucergy.ilocator.service.RegionsPredictor;
import com.ucergy.ilocator.service.spark.transformer.SparkPredictor;
import com.ucergy.ilocator.util.CsvParser;
import com.ucergy.ilocator.util.CsvUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by l_a_s on 04/06/2017.
 */
@Controller
@RequestMapping("/")
public class PredictionController {

    @Autowired
    RegionsPredictor regionsPredictor;

    @Autowired
    SparkPredictor sparkPredictor;

    @Autowired
    UserMovesRepository userMovesRepository;

    @RequestMapping(value = "/predictor", method = RequestMethod.POST)
    @ResponseBody
    public String getSearchUserProfiles(@RequestBody UserMovesReceived userData, HttpServletRequest request) throws IOException {

        System.out.println("************  Position reçue depuis le mobile:" + userData.getMobileId());
        System.out.println("************  Le client était dans:" + userData.getRegion());

        ArrayList<String> userPath = new ArrayList<>();
        ArrayList<String> userCategories = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        Date date = calendar.getTime();
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);


        String session = formattedDate + "-" + userData.getMobileId();
        String sessionFormated = session.replaceAll("\\s+", "-");
        // J'enregistre la position de l'utilsateur
        this.userMovesRepository.save(new UserMoves(UUIDs.timeBased(), sessionFormated, userData.getMobileId(), userData.getFloor(), userData.getRegion(), userData.getCategory(), date, userData.getDuration()));

        // J'écris dans le fichier temporaire
        List<String> userMovesReceivedList = new ArrayList<>();
        userMovesReceivedList.add(sessionFormated);
        userMovesReceivedList.add(userData.getMobileId());
        userMovesReceivedList.add(String.valueOf(userData.getFloor()));
        userMovesReceivedList.add(userData.getRegion());
        userMovesReceivedList.add(userData.getCategory());
        userMovesReceivedList.add(Integer.toString(userData.getDuration()));
        userMovesReceivedList.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userData.getDate()));

        String path = "target/classes/templates/tmp-Sessions/" + userData.getMobileId() + "-" + "tmp.csv";

        FileWriter writer = new FileWriter(new ClassPathResource(path).getPath(), true);

        CsvUtils.writeLine(writer, userMovesReceivedList);

        writer.flush();
        writer.close();

        String pathTmp = "templates/tmp-Sessions/" + userData.getMobileId() + "-" + "tmp.csv";

        // Je parse le fichier CSV temporaire
        userPath = CsvParser.ParseCsvFileAndGetColumn(pathTmp, 3);
        userCategories = CsvParser.ParseCsvFileAndGetColumn(pathTmp, 4);

        // Trouver le magasin à suggérer
        String nextDestination = regionsPredictor.getFrequentItem(userPath, userCategories);
        // String nextDestination = sparkPredictor.predict(userPath, userCategories);
        return nextDestination;
    }

    @RequestMapping(value = "/startSession", method = RequestMethod.POST)
    @ResponseBody
    public void startSession(@RequestBody UserMovesReceived userData, HttpServletRequest request) throws IOException, IOException {

        System.out.println("************ Creation de session Message reçu depuis le mobile:"+ userData.getMobileId());

        String path = "target/classes/templates/tmp-Sessions/" + userData.getMobileId() + "-" + "tmp.csv";
        FileWriter writer = new FileWriter(new ClassPathResource(path).getPath());
        writer.flush();
        writer.close();

    }

    @RequestMapping(value = "/stopSession", method = RequestMethod.POST)
    @ResponseBody
    public void stopSession
            (@RequestBody UserMovesReceived userData, HttpServletRequest request) throws IOException, IOException {

        String path = "target/classes/templates/tmp-Sessions/" + userData.getMobileId() + "-" + "tmp.csv";

        System.out.println("************** l'utilisateur :"+ userData.getMobileId()+ "a quitté la session  ");

        File file = new File(new ClassPathResource(path).getPath());

        if(file.exists() ) {
            System.out.println("Suppression de la session du mobile : "+ userData.getMobileId());
            file.delete();
        }else{
            System.out.println(" The file doesn't exist");
        }

    }
}
