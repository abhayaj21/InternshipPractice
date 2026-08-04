import { ChangeDetectorRef, Component, ElementRef, ViewChild, inject } from '@angular/core';
import { Departmentservice } from '../../service/departmentservice';
import { FormsModule } from '@angular/forms';
import { NgFor, NgIf } from '@angular/common';
import { HttpStatusCode } from '@angular/common/http';
import { ToastService } from '../../service/toast.service';
import { ConfirmService } from '../../service/confirm.service';

@Component({
  selector: 'app-departments',
  imports: [FormsModule, NgIf, NgFor],
  templateUrl: './departments.html',
  styleUrl: './departments.css',
})
export class Departments {
  departmentName = '';
  updatedDepartmentName = '';
  departmentId = 0;
  departmentsData: any[] = [];
  isServerRespond = false;
  @ViewChild('closebtn') btnclose!: ElementRef;

  private toast = inject(ToastService);
  private confirmService = inject(ConfirmService);

  constructor(private deptService: Departmentservice, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void { this.fetchAllDepartments(); }

  fetchAllDepartments() {
    this.deptService.getAllDepartments().subscribe({
      next: res => {
        if (res.status === HttpStatusCode.Ok) {
          this.departmentsData = res.body || [];
          this.isServerRespond = true;
        }
        this.cdr.detectChanges();
      },
      error: err => {
        this.isServerRespond = false;
        this.toast.error(err.error?.message || 'Could not load departments.', 'Server Down');
      }
    });
  }

  addDepartment() {
    this.deptService.addDepartment(this.departmentName.toUpperCase()).subscribe({
      next: res => {
        if (res.status == HttpStatusCode.Created) {
          this.toast.success(`"${this.departmentName.toUpperCase()}" department added successfully.`, 'Department Added');
          this.departmentsData.push(res.body);
          this.departmentName = '';
          this.isServerRespond = true;
        }
        this.cdr.detectChanges();
      },
      error: err => {
        this.isServerRespond = false;
        this.toast.error(err.error?.message || 'Could not add department.', 'Error');
      }
    });
  }

  async deleteDepartment(deptId: number, index: number) {
    const confirmed = await this.confirmService.confirm(
      'Delete this department?',
      'All students linked to this department may be affected.'
    );
    if (!confirmed) return;

    this.deptService.deleteDepartment(deptId).subscribe({
      next: res => {
        if (res.status === HttpStatusCode.NoContent) {
          this.toast.success('Department deleted successfully.', 'Deleted');
          this.departmentsData.splice(index, 1);
        }
        this.cdr.detectChanges();
      },
      error: err => {
        if (err.status == HttpStatusCode.InternalServerError) {
          this.toast.error('Internal server error occurred.', 'Server Error');
        } else {
          this.toast.error('Server is down. Please try again later.', 'Server Down');
        }
      }
    });
  }

  openUpdateDepartmentModal(index: number, deptId: number) {
    this.updatedDepartmentName = this.departmentsData[index].departmentName;
    this.departmentId = deptId;
  }

  updateDepartment() {
    this.deptService.updateDepartment(this.departmentId, this.updatedDepartmentName.toUpperCase()).subscribe({
      next: res => {
        if (res.status === HttpStatusCode.Ok) {
          this.toast.success('Department updated successfully.', 'Updated');
          this.updatedDepartmentName = '';
          this.departmentId = 0;
          this.btnclose.nativeElement.click();
        }
        this.fetchAllDepartments();
        this.cdr.detectChanges();
      },
      error: err => {
        this.toast.error(err.error?.message || 'Could not update department.', 'Error');
      }
    });
  }
}
