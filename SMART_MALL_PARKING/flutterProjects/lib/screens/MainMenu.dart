import 'package:flutter/material.dart';
import 'package:smart_mall_parking/screens/plateste_parcare_screen.dart';
import 'package:smart_mall_parking/screens/sesiune_parcare_screen.dart';
import '../models/sofer_model.dart';
import '../screens/setare_preferinte_screen.dart';
import '../screens/schimb_inmatriculare.dart';
import '../screens/welcome_screen.dart';
import '../screens/schimbare_parola.dart';
import '../screens/parking_map_screen.dart';
import '../services/rezervare_api_service.dart';
import 'locatie_parcare_screen.dart';



class MainMenuScreen extends StatelessWidget {
  final Sofer sofer;

  const MainMenuScreen({super.key, required this.sofer});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      extendBodyBehindAppBar: true, // fundalul se intinde sub AppBar
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        iconTheme: const IconThemeData(
          color: Colors.black, // sageata inapoi neagra
        ),
      ),
      body: Stack(
        fit: StackFit.expand,
        children: [
          // Fundal
          Image.asset('assets/funadal_principal.jpg', fit: BoxFit.cover),
          Container(color: Colors.black.withOpacity(0.4)), // overlay semi-transparent

          // Continut scrollabil
          SafeArea(
            child: Padding(
              padding: const EdgeInsets.all(24),
              child: Scrollbar(
                thumbVisibility: true,
                child: SingleChildScrollView(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.stretch,
                    children: [
                      const SizedBox(height: 10),
                      Text(
                        'Bine ai venit, ${sofer.nume}!',
                        style: const TextStyle(
                          fontSize: 22,
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                        textAlign: TextAlign.center,
                      ),
                      const SizedBox(height: 8),
                      Text(
                        'Nr. inmatriculare: ${sofer.nrInmatriculare}',
                        style: const TextStyle(fontSize: 16, color: Colors.white),
                        textAlign: TextAlign.center,
                      ),
                      const SizedBox(height: 24),

                      // MENIU
                      const Text(
                        'MENIU',
                        style: TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                            color: Colors.white),
                      ),
                      const Divider(color: Colors.white),
                      const SizedBox(height: 8),
                      OutlinedButton(
                        onPressed: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) =>
                                  SetarePreferinteScreen(sofer: sofer),
                            ),
                          );
                        },
                        child: const Text('Setare Preferinte', style: TextStyle(color: Colors.white)),
                        style: OutlinedButton.styleFrom(
                            side: const BorderSide(color: Colors.white),
                            padding: const EdgeInsets.symmetric(vertical: 14)),
                      ),
                      const SizedBox(height: 8),
                      OutlinedButton(
                        onPressed: () { Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => const ParkingMapScreen(),
                          ),
                        );},
                        child: const Text('Vizualizare Harta', style: TextStyle(color: Colors.white)),
                        style: OutlinedButton.styleFrom(
                            side: const BorderSide(color: Colors.white),
                            padding: const EdgeInsets.symmetric(vertical: 14)),
                      ),
                      const SizedBox(height: 8),
                      OutlinedButton(
                        onPressed: ()async {
                          try {
                            final loc = await RezervareApiService.rezervaLoc(sofer);

                            if (loc != null) {
                              ScaffoldMessenger.of(context).showSnackBar(
                                SnackBar(content: Text('Loc rezervat')),
                              );
                            } else {
                              ScaffoldMessenger.of(context).showSnackBar(
                                const SnackBar(content: Text('Nu s-a putut rezerva niciun loc')),
                              );
                            }
                          } catch (e) {
                            ScaffoldMessenger.of(context).showSnackBar(
                              SnackBar(content: Text('Eroare la rezervare: $e')),
                            );
                          }

                        },
                        child: const Text('Rezerva Loc', style: TextStyle(color: Colors.white)),
                        style: OutlinedButton.styleFrom(
                            side: const BorderSide(color: Colors.white),
                            padding: const EdgeInsets.symmetric(vertical: 14)),
                      ),
                      const SizedBox(height: 8),
                      OutlinedButton(
                        onPressed: () async {
                          try {
                            // 1️⃣ Luăm sesiunea curentă a șoferului
                            final sesiune = await RezervareApiService.getSesiuneCurenta(sofer.id);

                            if (sesiune == null) {
                              ScaffoldMessenger.of(context).showSnackBar(
                                const SnackBar(content: Text('Nu există o sesiune de parcare activă.')),
                              );
                              return;
                            }

                            // 2️⃣ Deschidem ecranul de plată și trimitem suma curentă
                            Navigator.push(
                              context,
                              MaterialPageRoute(
                                builder: (context) => PlatesteParcareScreen(
                                  sofer: sofer,
                                  suma: sesiune.costTotal, // preluăm costul curent
                                ),
                              ),
                            );
                          } catch (e) {
                            ScaffoldMessenger.of(context).showSnackBar(
                              SnackBar(content: Text('Eroare la obținerea sesiunii: $e')),
                            );
                          }
                        },
                        child: const Text('Plateste Parcare', style: TextStyle(color: Colors.white)),
                        style: OutlinedButton.styleFrom(
                            side: const BorderSide(color: Colors.white),
                            padding: const EdgeInsets.symmetric(vertical: 14)),
                      ),
                      const SizedBox(height: 8),
                      OutlinedButton(
                        onPressed: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => SesiuneParcareScreen(sofer: sofer),
                            ),
                          );
                        },
                        child: const Text('Parcare Activa', style: TextStyle(color: Colors.white)),
                        style: OutlinedButton.styleFrom(
                            side: const BorderSide(color: Colors.white),
                            padding: const EdgeInsets.symmetric(vertical: 14)),
                      ),
                      const SizedBox(height: 8),
                      OutlinedButton(
                        onPressed: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => LocatieParcareScreen(sofer: sofer),
                            ),
                          );
                        },
                        child: const Text('Locatia Masinii Parcate', style: TextStyle(color: Colors.white)),
                        style: OutlinedButton.styleFrom(
                            side: const BorderSide(color: Colors.white),
                            padding: const EdgeInsets.symmetric(vertical: 14)),
                      ),
                      const SizedBox(height: 24),

                      // CONT
                      const Text(
                        'CONT',
                        style: TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                            color: Colors.white),
                      ),
                      const Divider(color: Colors.white),
                      const SizedBox(height: 8),
                      OutlinedButton(
                        onPressed: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) =>
                                  SchimbareParolaScreen(sofer: sofer),
                            ),
                          );
                        },
                        child: const Text('Schimba Parola', style: TextStyle(color: Colors.white)),
                        style: OutlinedButton.styleFrom(
                            side: const BorderSide(color: Colors.white),
                            padding: const EdgeInsets.symmetric(vertical: 14)),
                      ),
                      const SizedBox(height: 8),
                      OutlinedButton(
                        onPressed: () async {
                          final newNr = await Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) =>
                                  SchimbInmatriculareScreen(sofer: sofer),
                            ),
                          );

                          if (newNr != null) {
                            ScaffoldMessenger.of(context).showSnackBar(
                              const SnackBar(content: Text('Date actualizate')),
                            );
                            //Navigator.pop(context); // exemplu de deconectare
                          }
                        },
                        child: const Text('Schimba Inmatriculare', style: TextStyle(color: Colors.white)),
                        style: OutlinedButton.styleFrom(
                            side: const BorderSide(color: Colors.white),
                            padding: const EdgeInsets.symmetric(vertical: 14)),
                      ),
                      const SizedBox(height: 8),
                      OutlinedButton(
                        onPressed: () {
                          Navigator.pushAndRemoveUntil(
                            context,
                            MaterialPageRoute(
                              builder: (context) => const WelcomeScreen(),
                            ),
                                (route) => false, // șterge TOT stack-ul
                          );
                        },
                        child: const Text('Deconectare', style: TextStyle(color: Colors.white)),
                        style: OutlinedButton.styleFrom(
                            side: const BorderSide(color: Colors.white),
                            padding: const EdgeInsets.symmetric(vertical: 14)),
                      ),
                      const SizedBox(height: 20),
                    ],
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
