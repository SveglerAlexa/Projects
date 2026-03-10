class SesiuneParcareDTO {
  final String oraInceput;  // primim ca String de la backend (ISO8601)
  final int minute;         // minute parcare simulate
  final double tarifPeOra;  // tarif pe ora
  final double costTotal;   // costul acumulat

  SesiuneParcareDTO({
    required this.oraInceput,
    required this.minute,
    required this.tarifPeOra,
    required this.costTotal,
  });

  // Factory pentru a converti JSON-ul de la backend în obiect Dart
  factory SesiuneParcareDTO.fromJson(Map<String, dynamic> json) {
    return SesiuneParcareDTO(
      oraInceput: json['oraInceput'] ?? '',
      minute: json['minute'] ?? 0,
      tarifPeOra: (json['tarifPeOra'] as num?)?.toDouble() ?? 0.0,
      costTotal: (json['costTotal'] as num?)?.toDouble() ?? 0.0,
    );
  }

  // Optional: metoda pentru a converti inapoi in JSON
  Map<String, dynamic> toJson() {
    return {
      'oraInceput': oraInceput,
      'minute': minute,
      'tarifPeOra': tarifPeOra,
      'costTotal': costTotal,
    };
  }
}
