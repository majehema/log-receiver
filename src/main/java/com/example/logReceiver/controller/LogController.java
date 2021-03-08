package com.example.logReceiver.controller;

import com.example.logReceiver.exception.BadRequestException;
import com.example.logReceiver.model.LogRest;
import com.example.logReceiver.proto.LogProto;
import com.example.logReceiver.LogReceiverApplication;
import com.example.logReceiver.util.SocketConnection;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Controller for Restfull requests
 */
@RestController
@ApiResponses(value = {
        @ApiResponse(code=400, message = "This is a bad request, please follow the API documentation for the proper request format."),
        @ApiResponse(code=200, message = "The data was successfully sent."),
})
public class LogController {

    /**
     * Receive de POST request and create the report according to the protobuf message schema
     * @param log JSON data
     */
    @PostMapping(path = "/log", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void receiveLog(@RequestBody LogRest log) {
        // Create the report
        LogProto.Report.Builder report = LogProto.Report.newBuilder();

        try {
            System.out.println("Creating report");

            // Create the log
            LogProto.Log.Builder lp = LogProto.Log.newBuilder();

            lp.setTstamp(log.getTimestamp());
            lp.setUserId(log.getUserId());
            lp.setEvent(log.getEvent());

            // Add a log to the report
            report.addLogs(lp);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
            throw new BadRequestException();
        }

        try {
            System.out.println("Opening python file");
            final Process p = Runtime.getRuntime().exec("python " + LogReceiverApplication.LOG_SAVER);

            // Print messages from python console
            new Thread(() -> {
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;

                try {
                    while ((line = input.readLine()) != null)
                        System.out.println(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            System.out.println("Sending protobuf message");
            String message = SocketConnection.runClient(report.build());
            p.waitFor();
            p.destroy();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
