import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { Studentservice } from '../../service/studentservice';
import { Departmentservice } from '../../service/departmentservice';
import { HttpStatusCode } from '@angular/common/http';
import { NgIf } from '@angular/common';
import { ToastService } from '../../service/toast.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashbordhome',
  imports: [NgIf],
  templateUrl: './dashbordhome.html',
  styleUrl: './dashbordhome.css',
})
export class Dashbordhome {
  totalStudents = 0;
  totalDepartments = 0;
  avrageMarks = 0;
  isServerRespond = false;
  private toast = inject(ToastService);
  private router = inject(Router);

  constructor(
    private studentService: Studentservice,
    private deptService: Departmentservice,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.studentService.fetchAllStudents().subscribe({
      next: (res) => {
        if (res.status === HttpStatusCode.Ok) {
          this.deptService.getAllDepartments().subscribe({
            next: (res) => {
              if (res.status === HttpStatusCode.Ok) {
                this.totalDepartments = res.body?.length || 0;
                this.isServerRespond = true;
              }
              this.cdr.detectChanges();
            },
            error: (err) => {
              this.toast.error(err.error?.message || 'Failed to load departments.', 'Server Error');
            },
          });
          this.totalStudents = res.body?.length || 0;
          if (this.totalStudents > 0) {
            let total = 0;
            res.body?.forEach((s: any) => { total += s.marks; });
            this.avrageMarks = Math.round(total / this.totalStudents);
          }
          this.isServerRespond = true;
        }
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.toast.error(err.error?.message || 'Unable to connect to server.', 'Server Down');
        this.isServerRespond = false;
        this.cdr.detectChanges();
      },
    });
  }

  goTo(path: string) { this.router.navigate([path]); }
}
