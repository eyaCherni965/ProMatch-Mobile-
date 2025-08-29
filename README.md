#  ProMatch – Mobile (Étudiants)

##  Description
ProMatch est une application mobile Android permettant aux **étudiants** de découvrir, filtrer et postuler à des stages via une interface moderne de type “swipe”.  
Elle complète l’application web en offrant une expérience intuitive aux étudiants.

##  Équipe
Projet réalisé par :  
Eya Cherni , Jyotsna Bhunjun, Éléa Charier, Aurélie Fidélia, Sara Hamed & Neda Javaheri.

##  Fonctionnalités principales (Mobile)
- Connexion / Inscription étudiant
- Navigation et filtre des offres de stage
- Swipe → droite = postuler, gauche = ignorer
- Gestion du profil étudiant (CV, infos perso)
- Consultation des candidatures et de leur statut
- Liste des stages aimés ("Favoris")

##  Technologies utilisées
- **Langage** : Java  
- **IDE** : Android Studio  
- **Frameworks / Libs** : Retrofit (API REST), ViewPager  
- **Backend** : Node.js + Express (API REST commune)  
- **Base de données** : SQL Server (hébergé sur Azure)  

## Structure du projet
/app/src/main/java/...      → code source Java (Activities, Adapters, Retrofit, etc.)  
/app/src/main/res/...       → ressources (layouts XML, styles, icons, images)  
/app/src/main/AndroidManifest.xml  
/build.gradle  
/settings.gradle



##  Installation & exécution
1. Cloner le dépôt :
   ```bash
   git clone https://github.com/eyaCherni965/ProMatch-Mobile-.git
   cd ProMatch-Mobile
Ouvrir le projet dans Android Studio.
Vérifier l’URL du backend dans RetrofitClient.java :
public static final String BASE_URL = "http://10.0.2.2:3000/";
Pour un backend déployé (ex: sur Azure ou Heroku), remplacer par l’URL publique.

Lancer l’application sur :
un émulateur Android (Pixel, Nexus, etc.) ou un appareil physique connecté en USB (activer le mode développeur).

##  Références
Figma – Mockups UI/UX /
OpenAI (ChatGPT) – Debugging & génération d’exemples /
YouTube – Inspirations design mobile
