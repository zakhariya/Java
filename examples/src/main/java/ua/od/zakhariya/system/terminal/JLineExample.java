package ua.od.zakhariya.system.terminal;


import org.jline.builtins.Completers;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.completer.AggregateCompleter;
import org.jline.reader.impl.completer.completer.EnumCompleter;
import org.jline.reader.impl.completer.completer.StringsCompleter;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.impl.DumbTerminal;

import java.io.*;

public class JLineExample {

    public static void main(String[] args) throws IOException {

        Terminal terminal;
        try {
            // Пытаемся создать нормальный системный терминал
            terminal = TerminalBuilder.builder().build();
        } catch (IOException e) {
            try {
                // Если мы в IntelliJ, создаем текстовый терминал вручную, избегая NPE
                terminal = new DumbTerminal(System.in, System.out);
            } catch (IOException ex) {
                throw new RuntimeException("Не удалось инициализировать резервный терминал", ex);
            }
        }

        InputStream inputStream = terminal.input();
        OutputStream outputStream = terminal.output();
//            //or
//            Reader reader = terminal.reader();
//            PrintWriter writer = terminal.writer();

        terminal.flush();

        //Note that these only work if the terminal wrapper
        //supports them – which the fallback dumb terminal may not.
        Size size = terminal.getSize();

//            Completer completer = new AggregateCompleter(
//                    new StringsCompleter("foo", "bar", "baz"),
//                    new Completers.FilesCompleter(Path.of("/baseDir")),
//                    new Completers.DirectoriesCompleter(Path.of("/baseDir")),
//                    new EnumCompleter(CompletersClass)
//            );

        Completer completer = new StringsCompleter("foo", "bar", "baz");

        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
//                    .history(new DefaultHistory())
//                    .option(LineReader.Option.HISTORY_IGNORE_DUPS, false)
//                    .variable(LineReader.HISTORY_FILE, Path.of("target/jline-history"))
//                    .variable(LineReader.HISTORY_SIZE, 5)
//                    .completer(completer)
                .build();

        // 3. Read input
//            String line = lineReader.readLine("> ", " <", '#', "Password");

        String line = lineReader.readLine("> ");
        terminal.writer().println("Read: " + line);

    }
}
