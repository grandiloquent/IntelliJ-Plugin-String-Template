package psycho;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.Doc;

public class GenerateAction extends AnAction {

    public static void execute(Document document, int start, int end, String value) {
        String p = "\\{\\{([^\\}]*)\\}\\}";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(value);
        String result = "";
        if (matcher.find()) {
            String n = matcher.group(1);
            if (Utils.isDigits(n)) {
                StringBuilder sb = new StringBuilder();
                int max = Integer.parseInt(n);
                for (int i = 1; i < max; i++) {
                    sb.append(value.replaceAll(p, Integer.toString(i))).append('\n');
                }
                result = sb.toString();
            }
        }
        if (result.length() == 0) return;
        String finalResult = result;
        Runnable runnable = () -> document.replaceString(start, end, finalResult);
        Application application = ApplicationManager.getApplication();

        if (application.isDispatchThread()) {

            application.runWriteAction(runnable);

        } else {
            application.invokeLater(() -> application.runWriteAction(runnable));
        }

    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {

        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = anActionEvent.getRequiredData(CommonDataKeys.PROJECT);

        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        Runnable runnable = () -> execute(document, start, end, selectionModel.getSelectedText());
        WriteCommandAction.runWriteCommandAction(project, runnable);
    }
}
