package spg.server.network;

import spg.server.auth.Email;
import spg.server.auth.PBKDF2;
import spg.server.database.Database;
import spg.shared.User;
import spg.shared.network.ClientConnection;
import spg.shared.security.Keychain;
import spg.shared.security.RSA;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ServerNetwork {

    public static final ServerNetwork INSTANCE = new ServerNetwork();

    private final Map<Integer, ClientConnection> clients = new HashMap<>();
    private Keychain keychain = new Keychain(
        new HashMap<>(Map.of(
            "private", new BigInteger("25511700227659760114625537756285753788736574683745515379476628726473155562013538675341241831420845540561343227283538958901013557312119850439800390040237871582749858625151125656152377657318166055259058910120068932728000043246007189392259865167087054923441291185145014961936032019577166064765463236764097028101753039069809526755072081650068077457433301413131941838862659366659052986223837824665449453490905584825343461837919737750971756977131200096132734071897225095462344790869443613620917101333956693421612776650902251081201655671607003085271819020708256265379864044813625284625796089995979581712900816825303580059713"),
            "public", new BigInteger("832457957063261493471099509623566916386266897046107474909212022513954413539453890084196085418455750480045172774454755808823180047923613011654638984912096922596470154981410846548554344306037347725879049969846482781198354706933530306038704257269336323255228272176176900156412710825057259706869842911173726390971248558724848278376149818356503612710114714694155021980583707898485185971572012204176988631723915268662935863997379239711456340067283957936354661697141755919999068649358211073597386670739895122640063520873073386463894412597973334437189242198611468108823163938491798048104682863057270242672973712457562213300926847548669837442876592513669557258869544410185854865508459704453779539624341640882303757595326512737395260820378193639847486086839718818445049478775846555532914803467889341341668846857791952146979396745553617674946426899437498028124143120043413345737500624377237709022727559268619337903215967150500022391249333945471391674432061655035114684897334820879142295789445642935105426477653585793245402854317885473273677341317519176578804949065442671658002426821957087529782892295195048212645540259277911854257375925405409187319676961705635006602097192894825882080840943091912699286522662215976410121131012415047080643446362182102895931828787232117953187934093580500062154784020384340387822002359770147696551831836220139707568379204281884773329605204522650055125861297892588836446279376859357644432596588892710801065791986433104671215860690839527929056650364516938491901063624439843110237029347913577984820121928848613454705075561717974466209642269582143820161259645647098504080314076540895798984393925979565397988062208082061996437904198822021608436193512708937139735411792627324684220395024258674842964434594682925703147951805228314683835811560245526414662160001401599337007965656075524279801993625932466023430721183879976436274243161740005951191578105916731918318907588341480073100578452695508994730393432252851310269812446843132924774691422097720801397145349257196286328621440958273370804587397183820616385598845445478841594056254055315187818990761489637053288164773511826056569266200870307820783738480310680433014724729905495800013199388217867728907679073894658435985688346726121413255611097621926043744671914627015821668222968225097452624640390932353554600978720772954147988081969490054237168867000446214964553299156617976896087809512131901249286070249262596143815790413822083605742268352965824579493321597265102057841548445494875082974201566331094577"),
            "modulus", new BigInteger("846525088247758055631114142687016338926819907706370410670008540068354656149738266596387510249268057079777211068805843588361804339620436907917963170080252937618402030737258664136189949528747864678710085216269783980841866159759439336846799740727806240214285440312675113347196286849469863253310548213503251643751597626963535138723912362618588326386636996257342839512832369797195672416919619670451686014231234074417075128157199863605339233846907202008360718279416544216056333476971784526740681832625190997911262827854110234705085757087987581826066528199686013795110847143240886564446109437682509610318379107376595373201355222161016320213983085590001952714313879686808095284454667178177057393734034502683817170983344248633968841327796291417938986381997202963038120148811456574852745263349756306167731748202590195501201498130669580434886073102283580737373555442936123746031120312749678877797800901757321519475918958514103250243454949857078166622917578445133660188488188900005007454700011491144160210219129433724969849027236559924842112570427238000047252890424458210202548608431238244695722615553847524746775425158736487723765382861436401134132179050849965620364312547905507105942056609854590456352779937633024941484119874360122945482154709313534469730681715951059399622872116986127971350977516204816101584787467936531448649256901107009004910293774989819104193091960525090637377580603451234639163452625283260677354996427402858907800526124360048220510650856023547824502409605152821392056855854016040648035388998292168316660793749264977308425261109096335870889787940486738965700572501596555278383802977229390281985771205540194872757922770916295544285899535855772254231833880646459980773472627962645544221316882222611345265134830378242223303832222768642551045993166860394585232709614852798082312974913118268099609524334238845632896882726811193039579994284957889896656222288907128499488018908449121851817641854737358737310136647940792805829394684951385557345102801556205706969288809619811427341072233238789797471269443868484641006525165460919931122702623279315648324235847433019513432231564042476404349505538839196157499198263331802963447643899113147127311650322504303416294786808760684067473941563665615563976546164580722244282108578301245583409631227455609745214008844776223289459311630309955622839178380173198920119108407435619271470858545674801709072706557661070646376260597087887749690320791280663768928116285692852957004998623163951403080426801909912013223659237352722707")
        )) // NOTE: Die sind nur da für den Testzweck (des generieren dauert mir immer zu lang xD)
    );

    /**
     * Initializes the server by generating a new RSA keypair.
     */
    public void initialize() {
        System.out.println("Initializing server network...");
        Thread timer = new Thread(() -> {
            System.out.println("Generating security keys...");
            double start = System.currentTimeMillis();
            double elapsed;
            while (true) {
                elapsed = System.currentTimeMillis() - start;
                if (elapsed % 500 == 0) {
                    System.out.print("\rTime elapsed: " + elapsed / 1000.0 + "s");
                }
            }
        }, "Time");
        if (keychain == null) {
            timer.start();
            keychain = RSA.INSTANCE.genKeyPair();
            System.out.println();
            System.out.println("Public: " + keychain.getPublicKey().toString().length());
            System.out.println("Private: " + keychain.getPrivateKey().toString().length());
            System.out.println("Secret: " + keychain.getModulus().toString().length());
            timer.stop();
        }
    }

    /**
     * Maps a client id to the communication channel.
     * @param id The client's uid.
     * @param connection The client's communication connection.
     */
    public void mapConnection(int id, ClientConnection connection) {
        clients.put(id, connection);
    }

    /**
     * Gets the communication channel for a client's uid.
     * @param id The client's uid.
     * @return The client's communication channel.
     */
    public ClientConnection getConnection(int id) {
        return clients.get(id);
    }

    /**
     * Removes a client's communication channel from the map.
     * @param id The client's uid.
     * @return The client's communication channel, which may be closed by then.
     */
    public ClientConnection removeConnection(int id) {
        return clients.remove(id);
    }

    public boolean registerClient(String uname, String email, String password) {
        System.out.println("Signup request for " + uname + " with email " + email + " and password " + password);
        String hashedPassword = PBKDF2.INSTANCE.hash(password.toCharArray());
        if (Database.INSTANCE.addEntry(uname, email, hashedPassword)) {
            System.out.println("Successfully registered user: " + uname + "!");
            return true;
        } else {
            System.err.println("Failed to register user: " + uname + "!");
            return false;
        }
    }

    public User loginClient(String email, String password) {
        System.out.println("Login request for " + email + " with password " + password);
        User user = Database.INSTANCE.getEntry(email);
        if (user != null && PBKDF2.INSTANCE.verify(
            password.toCharArray(), Objects.requireNonNull(user.getPassword())
        )) {
            System.out.println("Successfully logged in user: " + user.getUname() + "!");
            return user;
        } else {
            System.err.println("Failed to log in user: " + email + "!");
            return null;
        }
    }

    public boolean resetClientPassword(String email, String newPassword) {
        System.out.println("Account reset request for " + email);
        String hashedPassword = PBKDF2.INSTANCE.hash(newPassword.toCharArray());
        boolean successful = Database.INSTANCE.updatePassword(email, hashedPassword);
        if (successful) {
            System.out.println("Successfully reset password for: " + email + "!");
            return true;
        } else {
            System.err.println("Failed to reset password for: " + email + "!");
            return false;
        }
    }

    /**
     * Gets a user from the database as long as the requester is friends with the user.
     * @param sender The requesters' uid.
     * @param target The target's uid.
     * @return The user if the requester is friends with the target, null otherwise.
     */
    public User getUser(int sender, int target) {
        // Todo: check if sender is friends with target
        //  otherwise you could query any user in the network (reduced privacy)
        //  solve using an api request to the server!
        return Database.INSTANCE.getEntry(target);
    }

    /**
     * Returns the keychain of the server. This is mostly used for getting public encryption key + shared modulus.
     * @return The keychain of the server.
     */
    public Keychain getServerKey() {
        return keychain;
    }
}
