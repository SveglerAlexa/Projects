import 'dart:async';
import 'package:flutter/material.dart';
import '../services/rezervare_api_service.dart';
import '../models/sofer_model.dart';
import '../models/sesiune_parcare_dto.dart';

class SesiuneParcareScreen extends StatefulWidget {
  final Sofer sofer;

  const SesiuneParcareScreen({super.key, required this.sofer});

  @override
  State<SesiuneParcareScreen> createState() => _SesiuneParcareScreenState();
}

class _SesiuneParcareScreenState extends State<SesiuneParcareScreen> {
  SesiuneParcareDTO? sesiune;
  Timer? _timer;

  @override
  void initState() {
    super.initState();
    _loadSesiune();
    _timer = Timer.periodic(const Duration(seconds: 1), (_) => _loadSesiune());
  }

  Future<void> _loadSesiune() async {
    final s = await RezervareApiService.getSesiuneCurenta(widget.sofer.id);
    if (s == null) return;

    if (s.minute >= 50 && s.minute < 60) {
      // notificare cu 10 secunde inainte
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Mai ai 10 secunde până la finalul orei simulate!')),
      );
    }

    setState(() {
      sesiune = s;
    });
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      extendBodyBehindAppBar: true,
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        iconTheme: const IconThemeData(color: Colors.black),
        title: const Text('Parcare Curentă'),
      ),
      body: Stack(
        fit: StackFit.expand,
        children: [
          // fundal
          Image.asset('assets/funadal_principal.jpg', fit: BoxFit.cover),
          Container(color: Colors.black.withOpacity(0.4)),

          SafeArea(
            child: Padding(
              padding: const EdgeInsets.all(24),
              child: sesiune == null
                  ? const Center(
                child: CircularProgressIndicator(color: Colors.white),
              )
                  : Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  Text(
                    'Sesiunea ta curentă:',
                    style: const TextStyle(
                      fontSize: 22,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                    textAlign: TextAlign.center,
                  ),
                  const SizedBox(height: 16),
                  Card(
                    color: Colors.white.withOpacity(0.9),
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(12)),
                    child: Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Column(
                        children: [
                          Text(
                            'Timp parcurs: ${sesiune!.minute} minute',
                            style: const TextStyle(fontSize: 18),
                          ),
                          const SizedBox(height: 8),
                          Text(
                            'Cost curent: ${sesiune!.costTotal.toStringAsFixed(2)} RON',
                            style: const TextStyle(fontSize: 18),
                          ),
                          const SizedBox(height: 8),
                          Text(
                            'Tarif pe oră: ${sesiune!.tarifPeOra.toStringAsFixed(2)} RON',
                            style: const TextStyle(fontSize: 18),
                          ),
                        ],
                      ),
                    ),
                  ),
                  const SizedBox(height: 24),
                  OutlinedButton(
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    child: const Text(
                      'Înapoi',
                      style: TextStyle(color: Colors.white),
                    ),
                    style: OutlinedButton.styleFrom(
                        side: const BorderSide(color: Colors.white),
                        padding: const EdgeInsets.symmetric(vertical: 14)),
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
