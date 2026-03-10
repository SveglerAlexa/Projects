import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/sofer_model.dart';

class SchimbareParolaScreen extends StatefulWidget {
  final Sofer sofer;

  const SchimbareParolaScreen({super.key, required this.sofer});

  @override
  State<SchimbareParolaScreen> createState() => _SchimbareParolaScreenState();
}

class _SchimbareParolaScreenState extends State<SchimbareParolaScreen> {
  final parolaController = TextEditingController();
  final confirmareController = TextEditingController();

  bool _obscureParola = true;
  bool _obscureConfirmare = true;
  bool loading = false;

  Future<bool> schimbaParola() async {
    final url = Uri.parse(
        'http://10.0.2.2:8080/api/soferi/${widget.sofer.id}/parola');

    final response = await http.put(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'parola': parolaController.text,
      }),
    );

    return response.statusCode == 200;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      extendBodyBehindAppBar: true,
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        iconTheme: const IconThemeData(color: Colors.black),
      ),
      body: Stack(
        fit: StackFit.expand,
        children: [
          Image.asset('assets/funadal_principal.jpg', fit: BoxFit.cover),
          Container(color: Colors.black.withOpacity(0.4)),
          SafeArea(
            child: Padding(
              padding: const EdgeInsets.all(24),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  const SizedBox(height: 20),
                  const Text(
                    'Schimbare Parola',
                    style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                    textAlign: TextAlign.center,
                  ),
                  const SizedBox(height: 40),

                  // PAROLA NOUA
                  TextField(
                    controller: parolaController,
                    obscureText: _obscureParola,
                    style: const TextStyle(color: Colors.white),
                    decoration: InputDecoration(
                      labelText: 'Parola noua',
                      labelStyle: const TextStyle(color: Colors.white),
                      enabledBorder: const UnderlineInputBorder(
                          borderSide: BorderSide(color: Colors.white)),
                      focusedBorder: const UnderlineInputBorder(
                          borderSide: BorderSide(color: Colors.white)),
                      suffixIcon: IconButton(
                        icon: Icon(
                          _obscureParola
                              ? Icons.visibility_off
                              : Icons.visibility,
                          color: Colors.white,
                        ),
                        onPressed: () {
                          setState(() {
                            _obscureParola = !_obscureParola;
                          });
                        },
                      ),
                    ),
                  ),
                  const SizedBox(height: 16),

                  // CONFIRMARE PAROLA
                  TextField(
                    controller: confirmareController,
                    obscureText: _obscureConfirmare,
                    style: const TextStyle(color: Colors.white),
                    decoration: InputDecoration(
                      labelText: 'Confirma parola',
                      labelStyle: const TextStyle(color: Colors.white),
                      enabledBorder: const UnderlineInputBorder(
                          borderSide: BorderSide(color: Colors.white)),
                      focusedBorder: const UnderlineInputBorder(
                          borderSide: BorderSide(color: Colors.white)),
                      suffixIcon: IconButton(
                        icon: Icon(
                          _obscureConfirmare
                              ? Icons.visibility_off
                              : Icons.visibility,
                          color: Colors.white,
                        ),
                        onPressed: () {
                          setState(() {
                            _obscureConfirmare = !_obscureConfirmare;
                          });
                        },
                      ),
                    ),
                  ),
                  const SizedBox(height: 30),

                  // SALVEAZA
                  OutlinedButton(
                    onPressed: loading
                        ? null
                        : () async {
                      if (parolaController.text.length < 6) {
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(
                              content: Text(
                                  'Parola trebuie sa aiba minim 6 caractere')),
                        );
                        return;
                      }

                      if (parolaController.text !=
                          confirmareController.text) {
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(
                              content: Text('Parolele nu coincid')),
                        );
                        return;
                      }

                      setState(() => loading = true);
                      bool ok = await schimbaParola();
                      setState(() => loading = false);

                      if (ok) {
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(
                              content: Text('Parola schimbata')),
                        );
                        Navigator.pop(context);
                      } else {
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(
                              content: Text('Eroare la salvare')),
                        );
                      }
                    },
                    style: OutlinedButton.styleFrom(
                      side: const BorderSide(color: Colors.white),
                      padding: const EdgeInsets.symmetric(vertical: 14),
                    ),
                    child: Text(
                      loading ? 'Se salveaza...' : 'Salveaza',
                      style: const TextStyle(
                          color: Colors.white, fontSize: 18),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
