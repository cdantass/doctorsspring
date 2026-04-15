CREATE TABLE consultas (
   id BIGSERIAL PRIMARY KEY,
   doctor_id BIGINT NOT NULL,
   pacient_id BIGINT NOT NULL,
   data TIMESTAMP NOT NULL,

   CONSTRAINT fk_consultas_doctor_id
       FOREIGN KEY (doctor_id)
           REFERENCES doctors(id),

   CONSTRAINT fk_consultas_pacient_id
       FOREIGN KEY (pacient_id)
           REFERENCES pacients(id)
);