import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;

import '../models/sofer_model.dart';

class SchimbInmatriculareScreen extends StatefulWidget {
  final Sofer sofer;

  const SchimbInmatriculareScreen({super.key, required this.sofer});

  @override
  State<SchimbInmatriculareScreen> createState() =>
      _SchimbInmatriculareScreenState();
}

class _SchimbInmatriculareScreenState
    extends State<SchimbInmatriculareScreen> {

  late TextEditingController nrController;

  @override
  void initState() {
    super.initState();
    nrController =
        TextEditingController(text: widget.sofer.nrInmatriculare);
  }

  Future<bool> salveaza() async {
    final url = Uri.parse(
        'http://10.0.2.2:8080/api/soferi/${widget.sofer.id}/inmatriculare');

    final response = await http.put(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'nrInmatriculare': nrController.text,
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
        toolbarHeight: 50,
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
                    'Schimba Numar Inmatriculare',
                    style: TextStyle(
                        fontSize: 24,
                        fontWeight: FontWeight.bold,
                        color: Colors.white),
                    textAlign: TextAlign.center,
                  ),
                  const SizedBox(height: 40),

                  TextField(
                    controller: nrController,
                    style: const TextStyle(color: Colors.white),
                    decoration: const InputDecoration(
                      labelText: 'Numar inmatriculare',
                      labelStyle: TextStyle(color: Colors.white),
                      enabledBorder: UnderlineInputBorder(
                          borderSide: BorderSide(color: Colors.white)),
                      focusedBorder: UnderlineInputBorder(
                          borderSide: BorderSide(color: Colors.white)),
                    ),
                  ),

                  const SizedBox(height: 40),

                  OutlinedButton(
                    onPressed: () async {
                      bool ok = await salveaza();
                      if (ok) {
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(
                              content:
                              Text('Numar de inmatriculare actualizat')),
                        );
                        Navigator.pop(context, nrController.text);
                      } else {
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(
                              content: Text('Eroare la salvare')),
                        );
                      }
                    },
                    style: OutlinedButton.styleFrom(
                      side: const BorderSide(color: Colors.white),
                      padding:
                      const EdgeInsets.symmetric(vertical: 14),
                    ),
                    child: const Text(
                      'Salveaza',
                      style:
                      TextStyle(color: Colors.white, fontSize: 18),
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
