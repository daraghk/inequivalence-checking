package inequivalence.src.main;

import inequivalence.src.ParsedMethodSignature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TraceWritingUtils {

    public static StringBuilder createFailingOutputTrace(
            InequivalenceType inequivalenceType,
            List<Integer> randomActions,
            List<ParsedMethodSignature> listOfParameterlessCommonMethodSignatures,
            Object classOneObject,
            Object classTwoObject,
            int indexOfFailingRandomAction)
    {
        // Init the failing trace output and collect the names of the actions / methods used
        StringBuilder failingTraceOutput = new StringBuilder();
        List<String> actionsAsMethodNames = translateActionsToMethodNames(
                randomActions,
                listOfParameterlessCommonMethodSignatures
        );

        // Create the first line of the trace
        // E.g. Comparing java.util.HashMap vs. java.util.Hashtable
        String classOneName = classOneObject.getClass().getName();
        String classTwoName = classTwoObject.getClass().getName();
        failingTraceOutput.append(
                "Comparing " + classOneName + " vs. " + classTwoName
        );

        // Add the proposed actions to the output
        // E.g. Proposed Random Action Trace: [toString, clone, entrySet, size, size]
        failingTraceOutput.append("\nProposed Random Action Trace:\n\t" + actionsAsMethodNames + "\n");


        // Add the completed actions here to the trace output so it follows the proposed actions
        // E.g. Failing Action Trace: [toString]
        List<String> completedActionsMethodNames = translateActionsToMethodNames(
                randomActions.subList(0, indexOfFailingRandomAction + 1),
                listOfParameterlessCommonMethodSignatures
        );
        failingTraceOutput.append("Failing Action Trace:\n\t" + completedActionsMethodNames  + "\n");
        failingTraceOutput.append("Inequivalence Type:\n\t" + inequivalenceType  + "\n");
        failingTraceOutput.append("\n");

        return failingTraceOutput;
    }

    public static void writeFailingTraceToFile(String failingTrace) throws IOException {
        // Get the current date and use in the name of the failing trace file
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String today = dateFormat.format(new Date());

        // Find the current day's failing trace file or create it if it does not exist
        File failingTracesFile = new File(
                "failing-traces/" + today + "-failing-traces.txt"
        );
        failingTracesFile.getParentFile().mkdirs();
        failingTracesFile.createNewFile();

        // Append the failing trace to the current day's failing-trace file
        FileWriter fileWriter = new FileWriter(failingTracesFile, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(failingTrace);
        bufferedWriter.close();
    }

    private static List<String> translateActionsToMethodNames(
            List<Integer> randomActions,
            List<ParsedMethodSignature> methodSignatures
    ){
        List<String> methodNames = new ArrayList<>();
        for (int randomAction : randomActions) {
            methodNames.add(methodSignatures.get(randomAction).getName());
        }
        return methodNames;
    }

}
