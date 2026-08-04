import { ChangeDetectorRef, Component, ElementRef, ViewChild, inject } from '@angular/core';
import { Studentservice } from '../../service/studentservice';
import { HttpStatusCode } from '@angular/common/http';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Departmentservice } from '../../service/departmentservice';
import { ToastService } from '../../service/toast.service';
import { ConfirmService } from '../../service/confirm.service';

@Component({
  selector: 'app-dashboardviewstudent',
  imports: [NgFor, NgIf, ReactiveFormsModule, CommonModule, FormsModule],
  templateUrl: './dashboardviewstudent.html',
  styleUrl: './dashboardviewstudent.css',
})
export class Dashboardviewstudent {
  studentData: any[] = [];
  departments: any[] = [];
  paginatedStudentData: any[] = [];
  serverNotRespond = true;
  updateStudentForm!: FormGroup;
  @ViewChild('closebtn') btnclose!: ElementRef;

  activePage: any = 1;
  private totalItems = 0;
  private pageSize = 2;
  pages: number[] = [];
  direction = 'ASC';
  sortBy = 'rollNo';
  searchStudent = '';

  private toast = inject(ToastService);
  private confirmService = inject(ConfirmService);

  constructor(
    private studentService: Studentservice,
    private cdr: ChangeDetectorRef,
    private deptService: Departmentservice
  ) {}

  ngOnInit(): void {
    this.fetchAllStudent();
    this.applyPagination();
  }

  fetchAllStudent() {
    this.studentService.fetchAllStudents().subscribe({
      next: response => {
        if (response.status == HttpStatusCode.Ok) {
          this.studentData = response.body || [];
          this.totalItems = this.studentData.length;
          this.pages = this.getPages();
          this.serverNotRespond = false;
        }
        this.cdr.detectChanges();
      },
      error: err => {
        this.toast.error(err.error?.message || 'Unable to load students.', 'Server Error');
        this.serverNotRespond = true;
      }
    });
  }

  searchStudents() {
    if (this.searchStudent === '') {
      this.activePage === 'all' ? this.fetchAllStudent() : this.applyPagination();
      return;
    }
    this.studentService.searchStudent(this.searchStudent).subscribe(res => {
      if (res.status === HttpStatusCode.Ok) {
        if (this.activePage === 'all') this.studentData = res.body || [];
        else this.paginatedStudentData = res.body || [];
      }
      this.cdr.detectChanges();
    });
  }

  sortStudentData() {
    if (this.direction === 'none') { this.fetchAllStudent(); return; }
    this.studentService.sortingStudent(this.direction, this.sortBy).subscribe(res => {
      if (res.status === HttpStatusCode.Ok) {
        this.studentData = res.body || [];
        this.serverNotRespond = false;
      }
      this.cdr.detectChanges();
    });
  }

  applyPagination() {
    if (this.activePage === 'all') return;
    this.studentService.applyPagination(this.activePage, this.pageSize).subscribe({
      next: res => {
        if (res.status == HttpStatusCode.Ok) {
          this.paginatedStudentData = res.body || [];
          this.serverNotRespond = false;
        }
        this.cdr.detectChanges();
      },
      error: err => {
        this.toast.error(err.error?.message || 'Pagination error.', 'Error');
        this.serverNotRespond = true;
      }
    });
  }

  async deleteStudent(roll: number) {
    const confirmed = await this.confirmService.confirm(
      'Delete this student?',
      'This will permanently remove the student record.'
    );
    if (!confirmed) return;

    this.studentService.deleteStudent(roll).subscribe({
      next: res => {
        if (res.status == HttpStatusCode.NoContent) {
          this.toast.success('Student record has been deleted successfully.', 'Deleted');
        }
        this.fetchAllStudent();
      },
      error: err => {
        this.toast.error('Failed to delete student. Please try again.', 'Error');
      }
    });
  }

  fetchStudent(index: number) {
    this.deptService.getAllDepartments().subscribe(res => {
      this.departments = res.body || [];
      this.cdr.detectChanges();
    });
    this.updateStudentForm = new FormGroup({
      studentName: new FormControl('', Validators.required),
      rollNo: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      marks: new FormControl('', Validators.required),
      city: new FormControl('', Validators.required),
      department: new FormGroup({
        departmentId: new FormControl('', Validators.required),
      })
    });
    const data = this.activePage === 'all' ? this.studentData[index] : this.paginatedStudentData[index];
    this.updateStudentForm.patchValue(data);
  }

  updateStudent() {
    const roll = this.updateStudentForm.get('rollNo')?.value;
    const payload = {
      name: this.updateStudentForm.get('studentName')?.value,
      email: this.updateStudentForm.get('email')?.value,
      marks: this.updateStudentForm.get('marks')?.value,
      city: this.updateStudentForm.get('city')?.value,
      departmentId: this.updateStudentForm.get('department.departmentId')?.value
    };
    this.studentService.updateStudent(roll, payload).subscribe({
      next: res => {
        if (res.status == HttpStatusCode.Ok) {
          this.toast.success('Student record updated successfully.', 'Updated');
          this.btnclose.nativeElement.click();
        }
        this.updateStudentForm.reset();
        this.fetchAllStudent();
      },
      error: err => {
        this.toast.error('Failed to update student. Please try again.', 'Server Error');
      }
    });
  }

  private getPages(): number[] {
    const total = Math.ceil(this.totalItems / this.pageSize);
    return Array.from({ length: total }, (_, i) => i + 1);
  }

  getVisiblePages(): number[] {
    const chunkIndex = this.activePage !== 'all' ? Math.floor((this.activePage - 1) / 3) : 0;
    const start = chunkIndex * 3;
    return this.getPages().slice(start, start + 3);
  }

  changePage(event: Event) {
    if ((event.target as HTMLElement).closest('button')) this.applyPagination();
  }
  goToPage(page: number) { this.activePage = page; }
  showAll()   { this.activePage = 'all'; }
  prePage()   { this.activePage--; }
  nextPage()  { this.activePage++; }
}
