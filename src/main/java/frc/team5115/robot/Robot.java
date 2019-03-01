package frc.team5115.robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team5115.commands.startLoopers;
import frc.team5115.joysticks.InputManager;
import frc.team5115.subsystems.*;

public class Robot extends TimedRobot {

    public static InputManager im;
    public static ShuffleboardTab tab = Shuffleboard.getTab("main");

    public static Arm arm;
    public static Vacuum succ;
    public static Climber climb;
    public static Wrist wrist;
    public static Drivetrain drive;
    public static Limelight limelight;

    //Joystick joy;

    //Thread thread = new Thread(new Debug());

    public void robotInit() {
        arm = new Arm();
        succ = new Vacuum();
        //climb = new Climber();
        wrist = new Wrist();
        drive = new Drivetrain();
        limelight = new Limelight();

        im = new InputManager();

        Scheduler.getInstance().add(new startLoopers());

        //thread.start();
    }

    public void teleopInit(){
        im.findControllers();
    }

    public void robotPeriodic() {
        if (RobotState.isEnabled()) {
            Scheduler.getInstance().run();
        }
    }

//                                                                                               WX0OkxxxO0KN
//                                                                                            WN0xollllllccld0N
//                                                                                           W0dllllllllccc:::kNMM
//                                                                                         WXkolllllllllcc::;;ck0OxxdddddkKW
//                                                                                        WKdllllllllllcc::;;;:clllollllc:cxXW
//                                                                                     WWWWKxxkxdoolllcc:::;;:cllllllllcc::;cxXW
//                                                                              WNXK0Okkxxk0KNWWNX0xolc::;::clllllolllcc::;;,;lx0N
//                                                                           NX0kxoolllllox0XXXN MMN0xl:;;;:lllllllllcc:::;,,,:llokK
//                                                                        WXOxooooooooooodO0d;,dNMWX0kxlclox0K0Oxdllcc:::;;,'';clcclx0
//                                                                      NKxolooooooooooooxOd'..:KWX0OxdloOKXWMMMWX0xlc::;;,'..;llllcccd0
//                                                                    NKxollooooooooooooodxc',,l0K0OxdolxKK0OKWMMMN0xo:;;,,'.':lllllccclxKW
//                                                                  WNkolooooooooooooooooooolccoxxxxdoloOko:.,kWWNKOkdl:,,'..;llllllllccclxXW
//                                                                WX0xoc;,;cooooooooooooooooooooooooolldx:...,xNNKOkxol:,''.,clllllllllcccclO
//                                                             WN0kdool:'.,cooooooooooooooooooooooooooool:,,,:kXKOkxdl:;''';cllllllllllccccccxXW
//                                                           WXOxooooooocclooooooooooooooooooooooooooooooollloxOkkxdlc;,',:cllllccccccccc:cc::o0W
//                                                          N0doooooooooooooooooooooooooolc:;:clooooooooooooooooooolc;,,;:cccclcccccccccc::::::ckN
//                                                        WXkoooooooooooooooooooooooool:;'.....'coooooooooooooollllc:;::cccllllllcccccccc:::::;;:dKW
//                                                       N0doooooooooooooooooooooooooolcccc:;,',cooooooooooooooollcccccclllllllllcccccccc:::::;;;;lOW
//                                                      NOoooooooooooooooooooooooooooooooooooolooooooooooooooooolllcccclllllllllllccccccc::::::;;;,:xN
//                                                     NOolooooooooooooooooooooooooooooooooooooooooooooooooooooolllccccllllllllllcccccccc::::::;;;;,,oK
//                                                    W0doooooooooooooooooooooooooooooooooooooooooooooooooooooooolllccclllllllllcccccccc::::::;;;;,,..:K
//                                                   WKdooooooooooooooooooooooooooooooooooooooooooooooooooooooooollllcccllccccccccccccc::::::;;;;,,'...oN
//                                                   Xxoooooooooooooooooooooooooooooooooooooooooooooooooooooooollllllcccccccccccccccc:::::::;;;;,,,.. .,OW
//                                                   Kdooooooooooooooooooooooooooooooooooooooooooooooooooooooolllllccccccccccccccc:::::::;;;;;;,,''.. ..lN
//                                                  WOooooooooooooooooooooooooooooooooooooooooooooooooooooooolllllccc:::ccccccc:::::::::::::;;,,''......;0
//                                                  Nkoooooooooooooooooooooooooooooooooooooooooooooooooooooolllllccc::::cccccc::::::coddxxddolc;'.......'xW
//                                                  Xxloooooooooddddoooooooooooooooooooooooooooooooooooooollllllccc:::;;:cccc:::cldxkOOkkkxddolc;........lX
//                                                  Xxlooooooooooddddooooooooooooooooooooooooooooooooooooollllcccc:::;;;:cc:::coxO00OOkkxddollc:;,.......cX
//                                                  Nklloooooooooooooooooooooooooooooooooooooooooooooolllllllcccc::;;,,;::::coxO000OOkkxdollc::;,'...  ..lN
//                                                  WOolllooooooooooooooooooooooooooooooooooooooollllllllcccccc:::;;,,',:::cdO000OOOkxxdolcc::;,'....   .dW
//                                                   Xdcllloooooooooooooooooooooooooooooooooollllllllllcccccc::::;;,,'',:clx0K00OOkkxddolc::;,'.....    ;0
//                                                   W0occllllllooooooooooooooooooooooooolllllllllllccccccc::::;;;,''..':ok0000Okkxxdolcc:;,,'......   .dW
//                                                    WOc:cccllllllllloooooooooooooooolllllllccccccccccc::::::;;,,'....,lk00OOOkkxdoolc::;,'........   :K
//                                                     Xxc::ccccclllllllllllllllllllllllllcccccccccccc:::::::;;,'......;xOOOkkkxddolcc:;;,'........   .:0W
//                                                      Nkl:::::ccccccccclllllllllllllcccccc:::::::::::::::;;;,'.......ckkkkxxdoolc::;;,,'........    ..cX
//                                                       WKo:;;::::::::ccccccccccccccc:::::::::::::::::;;;;;,'........'cxxxdoollc::;,,''.........     ...xW
//                                                       WXxc:;;;;:::::::::::::::::::::::;;;;;:;;;;;;;;;,,''..........,loolllc::;;,''....................cX
//                                                      WKxollc:;;;;;;;;;;::;;;;;;;;;;;;;;;;,;;;;;,,,,''.............';cc:::;;,,''.............',;,'.....,0
//                                                     N0dllooolc;,,,,,,;;;;;;;;;;;;,,,,,,,'''''''''...............',,,,,,,'''...............';:::::;'....xW
//                                                    NOolloooooolc:;,'''',,,,,,,,,''''''........................;oxxl:,................',;;;:c::::::;,...:0XNWW
//                                                   NOollooooooooodkkdl:,''''''''...........................';coxkxxol:,.........''',;:ccc::cccc:::::;;,,;lodxkO0KXW
//                                                  NOollooooolllokKNNNNXOxoc;,'.........................'',;clllcc::;;,,,;;:::ccccccllllcccclllcc:::::;;;:cclllllodxOKXW
//                                                 WKdllooooolllokXNNWWWWWWWWXKOxdoc:;;,,''',,;;;;;;;,,;;;:::;;::::c:::::cclllllllllllllllllooollc::::::::;:::ccclllllodkKW
//                                                 XxlllllllooloxKNNWWWWWMMMMMMMMMMWNNXXKKKKKXXNNNNXXK0000000000KKXXKxlllllllllllllllllooooooooolc:::::::::;;;::ccccccccclkN
//                                  WWNNXKKKKXNWW W0olllloooooodOXNNWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllllloooooooooollc::;::::::::;;;::cccccccclOW
//                               WWKOxolcccccclodkKkllllooooooox0XNWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollllllllllllooooooooooollc:;;;::::::::;;;:::ccccc::dN
//                             WN0dl:::::::::::;;;:ccllllooooodkKNNWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxollllllllllooooooooooolllc:;,;:::::::::;;;;;:ccc::,lX
//                            WXxlcllc:::::::::;;;;;:llllooooodOXNNWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllloooooooooooolllcc:;,;;::::::::;;;,;;:::;,'cX
//                           WXkdxO00Odl:::::::;;;;::clllooooodOXNNWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxolllllloooooooooooolllcc:;,',;;:::::::;;;,,,,;,,'.lX
//                         WXOxdxkO0KKKOdl::cc::;;;;:clllooooodkKNNWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllooooooooooolllcc::;'',;;;;;;:;;;;,,,,,,,''.lX
//                         XkdodddddkO000kdl:::;;;;;:clloooooookKXNNWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollllooooooooooollllcc:;,'',;;;;;;;;;;,,,,''','''dN
//                        WKxdddddddddxkO0Okdl:;;;;;:cllooooooox0XNNWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxollooooooooooollllcc::,'.';;;;;;;;;,,,,'''''''',k
//                         XkddddddddddddxkOkkdl:;:cllloooooolld0XXNWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWNOollooooooooolllllcc::;'..,;;;;;;,,,,,''''..''''cK
//                         NOddddddddddddddxkkxxoccllllooollllldOKXNNWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWKxooooooooooollllcc::;,..',;;,,,,,,'''''......''lX
//                         WXkdddddddddddddddxxdollllllllllllccok0KXNNWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWWNNXKOxooloooooooollllcc::;,...',,,,,,''''............;kW
//                          W0dooddddddddddddddollllllllllllcc:lk00KXXNNWWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMWWWWWWWWWWWNX0kxdoooollooooooolllcc::;;,'..'',''''''...............'oN
//                           W0dodddddddddddddolccclccclllllcc:lx00KKKXXNNWWWWWWWWWMMMMMMMMMMMMMWWWWWWWWWWWWWWNNKOkdoooooollllllllllllllcc:;;,,,'''''......................lX
//                            N0ddddddddddddddoccc::;,:clllcc:;:x00KKKXXXXNNWWWWWWWWWWWWWWWWWWWWWWWWWWWNNNNNNX0kdoooooooolllllllllllllccc:;;,,,..'''.......................cX
//                             WKxdodddddddddooc:;,'';cclccc:;';dO00KKXXXNNNNNWWWWWWWWWWWWWWWWWWWWWNNNNNNXXKOxooooooooooollllllllllllcc::;,,'...'''......',;:ccc:;,'.......lN
//                              WXOddddddddooooo:'..,:cccc::,'..oOO0KKKXXXNNNNNNNNNNNNNNNNNNNNNNNNNNXXXXKOkxoooooooooooolllllllllccccc::;,.....''''...';:lllllllcc:;;'.....lN
//                                N0xddddddoodddo:'.,;:::;;'....cxkO000KKXXXNNNNNNNNNXXXXXXXXXXXXXXXKK0Oxoooooooooooollllllllccccc:::;;,.....''''''',:looolllllc::;;;,'...'xW
//                                  WNOxddoododoool;.'',,,'......;oxkkO00KKXXXNNNNNNNXXXXXXXXXKKKKKKKK0kdoooooooooollllllllccccc:::;,'.....'',''''';cloooollllcc::;;,,,'...cX
//                                   WKxoooooooool:;;;,'....',,,,:oxkkOO00KKXXXNNXXXXXXXXXXXKKKKKKKK0Odoooooooooolllcccccc::::;;:;;,,'',,,,,,''';cloooollllccc::;,,,,,'..:K
//                                     NOddddoooooooddoc;,,;;;;,,,';ldxkOO000KKXXXXXXKKKKKKKKKKKKKK00Oxolooooooolllllcc::;,,,;;::cc::::::::;;,,,:loooolllcccc::;;;,,,,'''lK
//                                     W0xddddododddxxxxoc:;;;,,,'..,coxkkOOO00KKKKKKKKK000000000000Oxolooolllolllllcc::,..';:::::::::::;:::;;;;::lllllcccc:::;;;;;,,'..:K
//                                      NOdddddddddddddddoc:;;,,,'...';ldxxkkkOO0000000000OOOOOOOOOOxolllllllllllllcc::;,',;:ccllooolllc::;:;;;;;;;::ccc:::::;;;;;,'....cX
//                                      WXkdddddooddoodooolc;,,''.....';loodxxxkkkOOOOOOOOOOOkkkkkkdollllcclllllllcc::;;,;cdxkO00KKK00Okdl::;;;;;;;;,;;::::::::;;,'.....lN
//                                      WXkddddoooooooolcc:;,'....:dk0K0xlllooddxxkkkkkkkkkkkkkxxdollllc:;;:clcccc::;;,;ldxxkkkOO00KKKK0Oxoc;;;;;;,,,,;;:::::;,'......'xW
//                                        WNOdooddooooool:;;,,'...;K     W0o:cclllodddxxxxxxxxxxxdolccccc:,',;cccc::;;;:loooooooodddxkkO00OOdl:;;;;,,,,,;:::;,'........:K
//                                         WKxdoddoooooo:'''....;OW        Nkl:;::cclloooooodddddool::::::;;,';::::;;;clooooooooooooooddxkkkkxl:;;;,,,,'',;,'..........d
//                                           WN0kddooooooc,....:dXW         NOo;,;;:::ccccllllllllc:;;;;;;;,'.';;;;,;loooooooooooooooooooddxdol:;,,,,''..............cK
//                                              WNKOxddooooloxOKNW             W0d:,,,,,,;;;;:::::::;;,,,,''......'..,coooooooooooooooooooooooolc;,,''...............:0
//                                                WNX0O00XNW                     WXkl;'.'''''',,,,,,,''..............'coooooooooooooooooooooooolc:,''...... ........:0
//                                                                                  WKkl,.............................;loooooooooooooooooooooool:,'.... .   .......oX
//                                                                                    WXOdc;'.........................:loooooooooooooooooooooooc,....  .   ....'l0W
//                                                                                         WNKOxdlc:;,,,'''............,coooooooooooooooooooooool;....  .....':dKW
//                                                                                               WWNXXXKK000OOkkkkkOOxlodooooooooooooooooooooooo:'.. ....lxOKW
//                                                                                                                  Nkoooooooooooooooooooooooooo:'......oN
//                                                                                                                  WKdoooooooooooooooooooooooool,..  .'dX
//                                                                                                                  0dooooooooooooooooooooooooc,....;o0W
//                                                                                                                  Xkoooooooooooooooooooollc;'...,kNW
//                                                                                                                   WXkdooooooooooooooooooc;'.....oN
//                                                                                                                    WXOdoooooooooooooooool;'....cK
//                                                                                                                      N0xdoooooooooooooooo:'...oX
//                                                                                                                       WNKkdoooooooooooooo:',lON
//                                                                                                                         WX0kxdoooooooooddxKW




}

