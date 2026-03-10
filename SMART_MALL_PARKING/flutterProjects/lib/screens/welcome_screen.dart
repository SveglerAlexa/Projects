import 'package:flutter/material.dart';
import 'dart:ui';///pentru efectul de blur
import 'package:smart_mall_parking/screens/log_in.dart';
import 'package:smart_mall_parking/screens/inregistrare.dart';

import 'package:http/http.dart' as http;
import 'dart:convert';


///clasa asta defineste un ecran
///StatelessWidget->ecranul nu isi schimba starea
class WelcomeScreen extends StatelessWidget {
  const WelcomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold( ///structura de baza a ecranului, returneaza tot ce se vede pe pagina
      body: Stack(  ///permite suprapunerea elementelor
        fit: StackFit.expand, ///folosim pentru imagine+blur+continut
        children: [
          /// Imaginea de fundal
          Image.asset(
            'assets/p.png',
            fit: BoxFit.cover, ///face ca imaginea sa acopere tot ecranul
          ),

          /// Efect de blur peste imagine
          BackdropFilter(
            filter: ImageFilter.blur(sigmaX: 5, sigmaY: 5),
            child: Container(
              color: Colors.black.withOpacity(0.3),
            ),
          ),

          // Continutul principal
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 40),///adauga spatiu fata de marginile ecranului
            child: Column( ///coloana principala, aranjeaza elementele pe verticala
              mainAxisAlignment: MainAxisAlignment.spaceBetween, ///pune un element sus, unul la mijloc si unul jos
              children: [
                // Textul de sus
                const Text(
                  'SmartMall Parking',
                  style: TextStyle(
                    fontSize: 28,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),

                // Zona din mijloc
                Column(
                  children: const [
                    Icon(
                      Icons.local_parking,
                      size: 80,
                      color: Colors.white,
                    ),
                    SizedBox(height: 20),
                    Text(
                      'Bine ai venit!',
                      style: TextStyle(
                        fontSize: 32,
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                      ),
                    ),
                  ],
                ),

                // Zona de jos cu butoane
                Column(
                  children: [
                    SizedBox(
                      width: double.infinity,
                      child: OutlinedButton( ///buton fara background
                        onPressed: () {
                          // aici vei pune navigarea la inregistrare
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => const RegisterScreen(),
                            ),
                          );
                        },
                        style: OutlinedButton.styleFrom(
                          side: const BorderSide(color: Colors.white),///culoarea conturului butonului
                          padding: const EdgeInsets.symmetric(vertical: 14),///face butonul mai inalt
                        ),
                        child: const Text(
                          'Inregistrare',
                          style: TextStyle(
                            fontSize: 18,
                            color: Colors.white,
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(height: 12),
                    SizedBox(
                      width: double.infinity,
                      child: OutlinedButton(
                        onPressed: () {
                          // aici vei pune navigarea la autentificare
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => const LoginScreen(),
                              ),
                          );
                        },
                        style: OutlinedButton.styleFrom(
                          side: const BorderSide(color: Colors.white),
                          padding: const EdgeInsets.symmetric(vertical: 14),
                        ),
                        child: const Text(
                          'Autentificare',
                          style: TextStyle(
                            fontSize: 18,
                            color: Colors.white,
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
