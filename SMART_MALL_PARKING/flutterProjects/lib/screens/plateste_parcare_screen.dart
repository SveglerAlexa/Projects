import 'package:flutter/material.dart';
import '../models/sofer_model.dart';
import '../models/plata_request.dart';
import '../services/plata_api_service.dart';

class PlatesteParcareScreen extends StatefulWidget {
  final Sofer sofer;
  final double suma;

  const PlatesteParcareScreen({super.key, required this.sofer, required this.suma});

  @override
  State<PlatesteParcareScreen> createState() => _PlatesteParcareScreenState();
}

class _PlatesteParcareScreenState extends State<PlatesteParcareScreen> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _cardController = TextEditingController();
  final TextEditingController _numeCardController = TextEditingController();
  final TextEditingController _expirareController = TextEditingController();
  final TextEditingController _cvvController = TextEditingController();

  bool _loading = false;

  Future<void> _plateste() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() => _loading = true);

    try {
      final request = PlataRequest(
        soferId: widget.sofer.id,
        suma: widget.suma,
        // poți adăuga și datele cardului aici dacă vrei
      );

      await PlataApiService.plateste(request);

      setState(() => _loading = false);

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Plata a fost efectuată cu succes!')),
      );

      Navigator.pop(context);

    } catch (e) {
      setState(() => _loading = false);
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Eroare la plată: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      extendBodyBehindAppBar: true,
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        iconTheme: const IconThemeData(color: Colors.black),
        title: const Text('Plătește Parcare', style: TextStyle(color: Colors.black)),
      ),
      body: Stack(
        fit: StackFit.expand,
        children: [
          Image.asset('assets/funadal_principal.jpg', fit: BoxFit.cover),
          Container(color: Colors.black.withOpacity(0.4)),
          SafeArea(
            child: Padding(
              padding: const EdgeInsets.all(24),
              child: Scrollbar(
                thumbVisibility: true,
                child: SingleChildScrollView(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.stretch,
                    children: [
                      const SizedBox(height: 20),
                      Text(
                        'Bine ai venit, ${widget.sofer.nume}!',
                        style: const TextStyle(fontSize: 22, fontWeight: FontWeight.bold, color: Colors.white),
                        textAlign: TextAlign.center,
                      ),
                      const SizedBox(height: 8),
                      Text(
                        'Nr. înmatriculare: ${widget.sofer.nrInmatriculare}',
                        style: const TextStyle(fontSize: 16, color: Colors.white),
                        textAlign: TextAlign.center,
                      ),
                      const SizedBox(height: 24),
                      Container(
                        padding: const EdgeInsets.all(20),
                        decoration: BoxDecoration(
                          color: Colors.white.withOpacity(0.9),
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: Form(
                          key: _formKey,
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.stretch,
                            children: [
                              Text(
                                'Suma de plată: ${widget.suma.toStringAsFixed(2)} RON',
                                style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                                textAlign: TextAlign.center,
                              ),
                              const SizedBox(height: 20),

                              // Număr card
                              TextFormField(
                                controller: _cardController,
                                keyboardType: TextInputType.number,
                                decoration: const InputDecoration(
                                  labelText: 'Număr card',
                                  border: OutlineInputBorder(),
                                ),
                                validator: (value) {
                                  if (value == null || value.length != 16) {
                                    return 'Introduceți un număr valid de card';
                                  }
                                  return null;
                                },
                              ),

                              const SizedBox(height: 16),

                              // Nume pe card
                              TextFormField(
                                controller: _numeCardController,
                                decoration: const InputDecoration(
                                  labelText: 'Nume de pe card',
                                  border: OutlineInputBorder(),
                                ),
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'Introduceți numele de pe card';
                                  }
                                  return null;
                                },
                              ),

                              const SizedBox(height: 16),

                              // Expirare + CVV
                              Row(
                                children: [
                                  Expanded(
                                    child: TextFormField(
                                      controller: _expirareController,
                                      keyboardType: TextInputType.number,
                                      decoration: const InputDecoration(
                                        labelText: 'Expiră (MM/YY)',
                                        border: OutlineInputBorder(),
                                      ),
                                      validator: (value) {
                                        if (value == null || !RegExp(r'^\d{2}/\d{2}$').hasMatch(value)) {
                                          return 'Format MM/YY';
                                        }
                                        return null;
                                      },
                                    ),
                                  ),
                                  const SizedBox(width: 12),
                                  Expanded(
                                    child: TextFormField(
                                      controller: _cvvController,
                                      keyboardType: TextInputType.number,
                                      obscureText: true,
                                      decoration: const InputDecoration(
                                        labelText: 'CVV',
                                        border: OutlineInputBorder(),
                                      ),
                                      validator: (value) {
                                        if (value == null || value.length < 3) {
                                          return 'CVV invalid';
                                        }
                                        return null;
                                      },
                                    ),
                                  ),
                                ],
                              ),

                              const SizedBox(height: 20),

                              // Buton plată
                              ElevatedButton(
                                onPressed: _plateste,
                                style: ElevatedButton.styleFrom(
                                  padding: const EdgeInsets.symmetric(vertical: 14),
                                  backgroundColor: Colors.green,
                                ),
                                child: _loading
                                    ? const SizedBox(height: 20, width: 20, child: CircularProgressIndicator(color: Colors.white, strokeWidth: 2))
                                    : const Text('Plătește', style: TextStyle(fontSize: 16)),
                              ),
                            ],
                          ),
                        ),
                      ),
                      const SizedBox(height: 40),
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
