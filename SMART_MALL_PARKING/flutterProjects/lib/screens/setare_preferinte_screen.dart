import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;

import '../models/sofer_model.dart';

class SetarePreferinteScreen extends StatefulWidget {
  final Sofer sofer;

  const SetarePreferinteScreen({super.key, required this.sofer});

  @override
  State<SetarePreferinteScreen> createState() =>
      _SetarePreferinteScreenState();
}

class _SetarePreferinteScreenState extends State<SetarePreferinteScreen> {
  String tipLocatie = 'INAUNTRU';
  int etaj = 1;
  String intrare = 'A';
  bool dizabilitati = false;

  Future<bool> salveazaPreferinte() async {
    final url = Uri.parse(
        'http://10.0.2.2:8080/api/preferinte/${widget.sofer.id}');

    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'inauntru': tipLocatie == 'INAUNTRU',
        'etaj': tipLocatie == 'AFARA' ? 0 : etaj,
        'intrarePreferata': intrare,
        'pentruPersoaneCuDizabilitati': dizabilitati,
      }),
    );

    return response.statusCode == 200;
  }

  @override
  Widget build(BuildContext context) {
    final dropdownTextStyle = const TextStyle(color: Colors.white);

    return Scaffold(
      extendBodyBehindAppBar: true,
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        iconTheme: const IconThemeData(color: Colors.black),
        toolbarHeight: 50, // bara mai mica
      ),
      body: Stack(
        fit: StackFit.expand,
        children: [
          Image.asset('assets/funadal_principal.jpg', fit: BoxFit.cover),
          Container(color: Colors.black.withOpacity(0.4)),
          SafeArea(
            child: Padding(
              padding: const EdgeInsets.all(24),
              child: SingleChildScrollView(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    const SizedBox(height: 20),
                    const Text(
                      'Setare Preferinte Parcare',
                      style: TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.bold,
                          color: Colors.white),
                      textAlign: TextAlign.center,
                    ),
                    const SizedBox(height: 30),

                    // TIP LOCATIE
                    const Text('Tip locatie', style: TextStyle(color: Colors.white)),
                    DropdownButton<String>(
                      value: tipLocatie,
                      dropdownColor: Colors.black87,
                      style: dropdownTextStyle,
                      items: const [
                        DropdownMenuItem(
                            value: 'INAUNTRU', child: Text('Inauntru')),
                        DropdownMenuItem(
                            value: 'AFARA', child: Text('Afara')),
                      ],
                      onChanged: (value) {
                        setState(() {
                          tipLocatie = value!;
                          if (tipLocatie == 'AFARA') etaj = 0;
                        });
                      },
                    ),
                    const SizedBox(height: 16),

                    // ETAJ
                    if (tipLocatie == 'INAUNTRU') ...[
                      const Text('Etaj', style: TextStyle(color: Colors.white)),
                      DropdownButton<int>(
                        value: etaj,
                        dropdownColor: Colors.black87,
                        style: dropdownTextStyle,
                        items: const [
                          DropdownMenuItem(value: 1, child: Text('Parter')),
                          DropdownMenuItem(value: 2, child: Text('Etaj 1')),
                        ],
                        onChanged: (value) {
                          setState(() => etaj = value!);
                        },
                      ),
                      const SizedBox(height: 16),
                    ],

                    // INTRARE
                    const Text('Aproape de intrarea', style: TextStyle(color: Colors.white)),
                    DropdownButton<String>(
                      value: intrare,
                      dropdownColor: Colors.black87,
                      style: dropdownTextStyle,
                      items: const [
                        DropdownMenuItem(value: 'A', child: Text('Intrare A')),
                        DropdownMenuItem(value: 'B', child: Text('Intrare B')),
                      ],
                      onChanged: (value) {
                        setState(() => intrare = value!);
                      },
                    ),
                    const SizedBox(height: 16),

                    // DIZABILITATI
                    SwitchListTile(
                      title: const Text('Persoane cu dizabilitati',
                          style: TextStyle(color: Colors.white)),
                      value: dizabilitati,
                      onChanged: (value) {
                        setState(() => dizabilitati = value);
                      },
                    ),
                    const SizedBox(height: 30),

                    // BUTON SALVEAZA
                    OutlinedButton(
                      onPressed: () async {
                        bool ok = await salveazaPreferinte();
                        if (ok) {
                          ScaffoldMessenger.of(context).showSnackBar(
                            const SnackBar(content: Text('Preferinte salvate')),
                          );
                          Navigator.pop(context);
                        } else {
                          ScaffoldMessenger.of(context).showSnackBar(
                            const SnackBar(content: Text('Eroare la salvare')),
                          );
                        }
                      },
                      style: OutlinedButton.styleFrom(
                        side: const BorderSide(color: Colors.white),
                        padding: const EdgeInsets.symmetric(vertical: 14),
                      ),
                      child: const Text(
                        'Salveaza',
                        style: TextStyle(color: Colors.white, fontSize: 18),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
