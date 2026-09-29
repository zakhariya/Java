package ua.od.zakhariya.system.terminal;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.StyleSet;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class LanternaExample {

    public static void main(String[] args) throws IOException {
        Terminal terminal = defaultTerminal();
//        Terminal terminal = swingTerminal();

        terminal.enterPrivateMode();
// Terminal functionality here
        terminal.exitPrivateMode();
//
//        terminal.setForegroundColor(TextColor.ANSI.RED);
//        terminal.enableSGR(SGR.BOLD);
//        terminal.putCharacter('H');
//        terminal.putCharacter('e');
//        terminal.putCharacter('l');
//        terminal.putCharacter('l');
//        terminal.putCharacter('o');
//        terminal.disableSGR(SGR.BOLD);
//        terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
//        terminal.setBackgroundColor(TextColor.ANSI.BLUE);
//        terminal.enableSGR(SGR.UNDERLINE);
//        terminal.putCharacter('W');
//        terminal.putCharacter('o');
//        terminal.putCharacter('r');
//        terminal.putCharacter('l');
//        terminal.putCharacter('d');

//        while (true) {
//            KeyStroke keystroke = terminal.readInput();
//            if (keystroke.getKeyType() == KeyType.Escape) {
//                break;
//            } else if (keystroke.getKeyType() == KeyType.Character) {
//                terminal.putCharacter(keystroke.getCharacter());
//                terminal.flush();
//            }
//        }

        Screen screen = terminalScreen(terminal);
//        Screen screen = defaultScreen();

        screen.startScreen();

        screen.setCharacter(5, 5,
                new TextCharacter('!',
                        TextColor.ANSI.RED, TextColor.ANSI.YELLOW_BRIGHT,
                        SGR.UNDERLINE, SGR.BOLD));
        screen.refresh();

        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.RED);
        text.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        text.putString(5, 5, "Hello");
        text.putString(6, 6, "World!");
        screen.refresh();

        TerminalSize newSize = screen.doResizeIfNecessary();
        if (newSize != null) {
            // React to resize
        }

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);
// Render GUI here
        gui.updateScreen();

        MessageDialog dialog = new MessageDialogBuilder()
                .setTitle("Message Dialog")
                .setText("Dialog Contents")
                .build();
        gui.addWindow(dialog);

        BasicWindow window = new BasicWindow("Basic Window");
//        window.setHints(Set.of(Window.Hint.CENTERED,
//                Window.Hint.NO_POST_RENDERING,
//                Window.Hint.EXPANDED));
        gui.addWindow(window);

        window.setComponent(new Label("This is a label"));

        BasicWindow basicWindow = new BasicWindow("Basic Window");
        Panel innerPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        innerPanel.addComponent(new Label("Left"));
        innerPanel.addComponent(new Label("Middle"));
        innerPanel.addComponent(new Label("Right"));

        Panel outerPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        outerPanel.addComponent(new Label("Top"));
        outerPanel.addComponent(innerPanel);
        outerPanel.addComponent(new Label("Bottom"));

        basicWindow.setComponent(outerPanel);


        TextBox textbox = new TextBox();
        Button button = new Button("OK");
        button.addListener((b) -> {
            System.out.println(textbox.getText());
            window.close();
        });

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(textbox);
        panel.addComponent(button);

    }

    private static Terminal defaultTerminal() {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            return terminal;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Terminal swingTerminal() {
        try (Terminal terminal = new SwingTerminalFrame()) {
            return terminal;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Screen defaultScreen() {
        try (Screen screen = new DefaultTerminalFactory().createScreen()) {
            return screen;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Screen terminalScreen(Terminal terminal) {
        try (Screen screen = new TerminalScreen(terminal)) {
            return screen;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
