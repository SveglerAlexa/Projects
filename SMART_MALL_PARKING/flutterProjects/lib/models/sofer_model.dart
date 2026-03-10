class Sofer {
  final int id; // adaugat
  final String nume;
  final String email;
  final String nrInmatriculare;

  Sofer({
    required this.id,
    required this.nume,
    required this.email,
    required this.nrInmatriculare,
  });

  factory Sofer.fromJson(Map<String, dynamic> json) {
    return Sofer(
      id: json['id'], // preluam id-ul
      nume: json['nume'],
      email: json['email'],
      nrInmatriculare: json['nrInmatriculare'],
    );
  }
}
