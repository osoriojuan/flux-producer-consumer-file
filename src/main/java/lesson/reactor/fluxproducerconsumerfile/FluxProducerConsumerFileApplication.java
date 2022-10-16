package lesson.reactor.fluxproducerconsumerfile;

import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FluxProducerConsumerFileApplication {

    public static void main(String[] args) throws IOException {
        getPublisher()
                .log()
                .subscribe();
    }

    private static Flux<String> getPublisher() throws IOException {
        var file = new File("src/main/resources/FileToEmit");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        return Flux.create(synchronousSink -> {
            String line;
            try (fileReader){
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println("Emitting: ".concat(line));
                    synchronousSink.next(line);
                }
            } catch (Exception e) {
                synchronousSink.error(new IllegalAccessError("Error accessing to file: ".concat(e.getMessage())));
            }
            synchronousSink.complete();
        });
    }


}
