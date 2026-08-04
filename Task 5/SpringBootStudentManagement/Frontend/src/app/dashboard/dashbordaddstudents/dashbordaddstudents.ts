import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Studentservice } from '../../service/studentservice';
import { Departmentservice } from '../../service/departmentservice';
import { HttpStatusCode } from '@angular/common/http';
import { NgFor } from '@angular/common';
import { ToastService } from '../../service/toast.service';

@Component({
  selector: 'app-dashbordaddstudents',
  imports: [ReactiveFormsModule, FormsModule, NgFor],
  templateUrl: './dashbordaddstudents.html',
  styleUrl: './dashbordaddstudents.css',
})
export class Dashbordaddstudents {
  studentForm: FormGroup;
  departments: any[] = [];
  private toast = inject(ToastService);

  constructor(
    private fb: FormBuilder,
    private studentService: Studentservice,
    private deptService: Departmentservice,
    private cdr: ChangeDetectorRef
  ) {
    this.studentForm = fb.group({
      studentName: ['', Validators.required],
      rollNo: ['', Validators.required],
      marks: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      city: ['', Validators.required],
      deptId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.deptService.getAllDepartments().subscribe({
      next: res => {
        if (res.status == HttpStatusCode.Ok) {
          this.departments = res.body || [];
          this.cdr.detectChanges();
        }
      },
      error: err => {
        this.toast.error('Could not load departments. Please check server.', 'Server Down');
      }
    });
  }

  saveStudent() {
    const deptId = this.studentForm.get('deptId')?.value;
    const payload = {
      studentName: this.studentForm.get('studentName')?.value,
      rollNo: this.studentForm.get('rollNo')?.value,
      marks: this.studentForm.get('marks')?.value,
      email: this.studentForm.get('email')?.value,
      city: this.studentForm.get('city')?.value,
      delete: false
    };
    this.studentService.saveStudent(deptId, payload).subscribe({
      next: response => {
        if (response.status == HttpStatusCode.Created) {
          this.toast.success(`Student "${payload.studentName}" has been saved successfully.`, 'Student Added');
          this.studentForm.reset();
        }
      },
      error: err => {
        if (err.status == HttpStatusCode.Conflict) {
          this.toast.warning(err.error?.message || 'A student with this Roll No already exists.', 'Duplicate Entry');
        } else {
          this.toast.error('Unable to save student. Please try again.', 'Server Error');
        }
        this.studentForm.reset();
      }
    });
  }
}
